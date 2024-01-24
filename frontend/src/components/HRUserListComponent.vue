<template>
  <el-table :data="tableData" border stripe style="width: 100%">
    <el-table-column align="center" fixed label="序号">
      <template #default="{ $index }">
        <span>{{ $index + 1 }}</span>
      </template>
    </el-table-column>
    <el-table-column align="center" label="姓名" prop="name"/>
    <el-table-column align="center" label="用户名" prop="username"/>
    <el-table-column align="center" label="性别">
      <template #default="{ row }">
        <span>{{
            row.gender === null ? "" : row.gender === 0 ? "女" : "男"
          }}</span>
      </template>
    </el-table-column>
    <el-table-column align="center" label="电话" prop="phone"/>
    <el-table-column align="center" fixed="right" label="操作" width="230">
      <template #header>
        <div>
          <el-upload
              :limit="1"
              :show-file-list="false"
              :auto-upload="false"
              :on-change="registerAll"
              accept=".xlsx"
              ref="uploadFile"
              :key="uploadKey"
          >
            <el-button type="success" plain>批量注册</el-button>
          </el-upload>
        </div>
      </template>
      <template #default="scope">
        <el-button plain size="small" type="primary" @click="showDetailsDialog(scope.row.id)">用户详情
        </el-button>
        <el-button plain size="small" type="danger" @click="ElMessageBox.confirm(
            '确定要注销该用户吗？',
            '提示',
            {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }
        ).then(() => {
          writeOff(scope.row.id);
        }).catch(() => {
        })" :disabled="scope.row.userType !== 2">注销用户
        </el-button>
      </template>
    </el-table-column>
  </el-table>

  <el-pagination
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :page-sizes="[5, 10, 20, 50, 100]"
      :total="dataCount"
      layout="total, sizes, prev, pager, next, jumper"
      style="margin-top: 20px; text-align: right; margin-bottom: 20px"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
  />

  <el-dialog v-model="detailsDialogVisible" title="用户详情" width="80%">
    <div class="autowrap">
      <el-descriptions
          class="margin-top"
          :column="2"
          border
      >
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              姓名
            </div>
          </template>
          {{ details.name }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              电话
            </div>
          </template>
          {{ details.phone }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              性别
            </div>
          </template>
          {{ details.gender === null ? "" : details.gender === 0 ? '女' : '男'}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              邮箱
            </div>
          </template>
          {{ details.email }}
        </el-descriptions-item>
      </el-descriptions>

      <div style="height: 20px" />

      <el-table :data="userResumes" border stripe style="width: 100%">
        <el-table-column align="center" fixed label="序号">
          <template #default="{ $index }">
            <span>{{ $index + 1 }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="履历描述" prop="description" sortable/>
        <el-table-column align="center" label="开始时间">
          <template #default="{ row }">
            <span>{{ row.startTime ? row.startTime.substring(0, 10) : "" }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="结束时间">
          <template #default="{ row }">
            <span>{{ row.endTime ? row.endTime.substring(0, 10) : "" }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" fixed="right" label="操作" width="150px">
          <template #default="scope">
            <el-button plain size="small" type="primary"
                       @click="resumeDetail = scope.row.description; resumeDetailDialogVisible = true">查看详情
            </el-button>
          </template>
        </el-table-column>
      </el-table>

    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="detailsDialogVisible = false">关闭</el-button>
      </span>
    </template>
  </el-dialog>

  <el-dialog v-model="resumeDetailDialogVisible" title="简历描述详情" width="50%">
    <div class="autowrap">
      {{ resumeDetail === null ? "无" : resumeDetail }}
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="resumeDetailDialogVisible = false">关闭</el-button>
      </span>
    </template>
  </el-dialog>

</template>

<script setup>

import {ElMessageBox, ElMessage} from "element-plus";
import {defineProps, ref, watch} from 'vue';
import myAxios from '../Authorization-Axios.js'

let currentPage = ref(1);
let pageSize = ref(10);
let dataCount = ref(0);
let tableData = ref([]);
let detailsDialogVisible = ref(false);
let details = ref({});
let resumeDetailDialogVisible = ref(false);
let resumeDetail = ref("");
let userResumes = ref({});
let uploadFile = ref();
let uploadKey = ref(0);

// 用于接收父组件传递的数据
const props = defineProps({
  queryForm: {
    type: Object,
    required: true
  },
});

watch(() => props.queryForm, () => {
  pageSize.value = 10;
  currentPage.value = 1;
  search();
});

const writeOff = (userId) => {
  myAxios.delete(`http://localhost:8730/user/delete/${userId}`)
      .then((res) => {
        if (res.data) {
          ElMessage.success("注销成功!");
          currentPage.value = 1;
          search();
        } else {
          ElMessage.error("注销失败!");
        }
      })
      .catch(() => ElMessage.error("注销失败!"))
}

const registerAll = (file) => {
  const formData = new FormData();
  formData.append('users', file.raw);

  myAxios.post(`http://localhost:8730/user/import`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
      .then(response => {
        if (response.data.flag) {
          ElMessage.success('上传成功');
          search();
        } else {
          ElMessage.error(response.data.errorMsg);
        }
        uploadFile.value = "";
      })
      .catch(() => {
        ElMessage.error('上传失败');
        uploadFile.value = "";
      });
  uploadKey.value = uploadKey.value + 1;
}

const handleSizeChange = (val) => {
  pageSize.value = val;
  search();
};

const handleCurrentChange = (val) => {
  currentPage.value = val;
  search();
};

const showDetailsDialog = (userId) => {
  myAxios.get(`http://localhost:8732/user/detail/${userId}`)
      .then((res) => {
        details.value = res.data;
        userResumes.value = res.data.resumes;
        console.log(res.data);
        console.log(details.value);
        detailsDialogVisible.value = true;
      })
      .catch(() => {
        ElMessage.error("获取详细信息失败!");
        detailsDialogVisible.value = false;
      })
}

const search = () => {
  if (props.queryForm.startTime === undefined || props.queryForm.startTime === '1970-01-01')
    props.queryForm.startTime = 'null';
  if (props.queryForm.endTime === undefined || props.queryForm.endTime === '1970-01-01')
    props.queryForm.endTime = 'null';

  myAxios.get(`http://localhost:8731/user/list/${props.queryForm.name}/${props.queryForm.username}/${props.queryForm.phone}/${pageSize.value}/${currentPage.value}`)
      .then(res => {
            dataCount.value = res.data.totalCount;
            tableData.value = res.data.list;
          }
      ).catch(() => {
    ElMessage({
      message: "查询失败",
      type: "error",
      duration: 2 * 1000
    })
  });
}

search();

</script>

<style scoped>

.autowrap {
  width: 95%;
  word-wrap: break-word; /* 旧的浏览器版本 */
  overflow-wrap: break-word; /* 现代的浏览器版本 */
}

</style>