<template>
  <el-table :data="tableData" border stripe style="width: 100%">
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
      <template #header>
        <el-button plain type="primary" @click="addResumeStart">添加
        </el-button>
      </template>
      <template #default="scope">
        <el-button plain size="small" type="danger"
                   @click="ElMessageBox.confirm(
            '确定要删除吗？',
            '提示',
            {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }
        ).then(() => {
          delResume(scope.row);
        }).catch(() => {
        })"
        >删除
        </el-button>

        <el-button plain size="small" type="success"
                   @click="changeDialog(scope.row)">修改
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

  <el-dialog v-model="dialogVisible" :title="dialogTitle" width="50%">
    <span>
      <el-form label-width="100px">
        <el-form-item label="履历描述">
          <el-input v-model.trim="description" placeholder="请输入履历描述" required />
        </el-form-item>
        <el-form-item label="开始时间">
          <el-date-picker v-model="startTime" placeholder="请选择开始时间" required />
        </el-form-item>
        <el-form-item label="结束时间">
          <el-date-picker v-model="endTime" placeholder="请选择结束时间"/>
        </el-form-item>
      </el-form>
    </span>

    <template #footer>
      <span class="dialog-footer">
        <el-button type="success" @click="methodDistribution">{{
            dialogTitle === "添加履历" ? "添加" : "修改"
          }}</el-button>
        <el-button plain type="danger" @click="dialogVisible = false; resume = {}">关闭</el-button>
      </span>
    </template>
  </el-dialog>

</template>

<script setup>

import {ElMessageBox, ElMessage} from "element-plus";
import {ref} from 'vue';
import myAxios from '../../Authorization-Axios.js';

let currentPage = ref(1);
let pageSize = ref(10);
let dataCount = ref(0);
let tableData = ref([]);
let dialogVisible = ref(false);
// let resume = ref({});
// let oldResume = ref({});
let resumeId = ref(0);
let dialogTitle = ref("");
let description = ref("");
let startTime = ref(undefined);
let endTime = ref(undefined);

const addResumeStart = () => {
  resumeId.value = 0;
  description.value = "";
  startTime.value = null;
  endTime.value = null;
  dialogTitle.value = "添加履历";
  dialogVisible.value = true;
}

const methodDistribution = () => {
  if (dialogTitle.value === "添加履历") {
    addResume();
  } else {
    updateResume();
  }
}

const changeDialog = (row) => {
  dialogTitle.value = "修改履历";
  resumeId.value = row.id;
  description.value = row.description;
  startTime.value = row.startTime !== null ? new Date(row.startTime) : null;
  endTime.value = row.endTime !== null ? new Date(row.endTime) : null;
  dialogVisible.value = true;
}

const addResume = () => {
  let resume = ref({
    description: description.value,
    startTime: startTime.value,
    endTime: endTime.value,
    id: resumeId.value
  })

  if (resume.value.description === undefined || resume.value.description.length === 0) {
    ElMessage({
      message: "请输入履历描述",
      type: "error",
      duration: 2 * 1000
    })
    return;
  }

  if (resume.value.startTime === undefined || resume.value.startTime === null) {
    ElMessage({
      message: "请选择开始时间",
      type: "error",
      duration: 2 * 1000
    })
    return;
  }

  if (resume.value.endTime !== undefined && resume.value.endTime < resume.value.startTime) {
    ElMessage.error("非法时间段!");
    return;
  }

  if (resume.value.startTime) {
    resume.value.startTime = resume.value.startTime.toLocaleString('zh-CN', {
      year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit'
    }).split(' ')[0].replaceAll('/', '-').toString().split('T')[0];
  }

  if (resume.value.endTime) {
    resume.value.endTime = resume.value.endTime.toLocaleString('zh-CN', {
      year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit'
    }).split(' ')[0].replaceAll('/', '-').toString().split('T')[0];
  }

  myAxios.post(`http://localhost:8730/user/resume/add/${localStorage.getItem("User-Id")}`, resume.value)
      .then(res => {
        if (res.data) {
          ElMessage({
            message: "添加成功",
            type: "success",
            duration: 2 * 1000
          })
          dialogVisible.value = false;
          resume.value = {};
          currentPage.value = 1;
          pageSize.value = 10;
          search();
        }
        else {
          ElMessage({
            message: "添加失败",
            type: "error",
            duration: 2 * 1000
          })
        }
      })
      .catch(() => {
        ElMessage({
          message: "添加失败",
          type: "error",
          duration: 2 * 1000
        })
      })
}

const delResume = (row) => {
  myAxios.delete(`http://localhost:8730/user/resume/delete/${row.id}`)
     .then(res => {
        if (res.data) {
          ElMessage({
            message: "删除成功",
            type: "success",
            duration: 2 * 1000
          })
          currentPage.value = 1;
          pageSize.value = 10;
          search();
        }
      })
     .catch(() => {
        ElMessage({
          message: "删除失败",
          type: "error",
          duration: 2 * 1000
        })
      })
}

const updateResume = () => {
  let resume = ref({
    description: description.value,
    startTime: startTime.value,
    endTime: endTime.value,
    id: resumeId.value
  })

  if (resume.value.description === undefined || resume.value.description.length === 0) {
    ElMessage({
      message: "请输入履历描述",
      type: "error",
      duration: 2 * 1000
    })
    return;
  }

  if (resume.value.startTime === undefined || resume.value.startTime === null) {
    ElMessage({
      message: "请选择开始时间",
      type: "error",
      duration: 2 * 1000
    })
    return;
  }

  if (resume.value.endTime !== undefined && resume.value.endTime < resume.value.startTime) {
    ElMessage.error("非法时间段!");
    return;
  }

  console.log(resume.value.startTime);
  console.log(resume.value.endTime);

  if (resume.value.startTime) {
    resume.value.startTime = resume.value.startTime.toLocaleString('zh-CN', {
      year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit'
    }).split(' ')[0].replaceAll('/', '-').split('T')[0];
  }

  if (resume.value.endTime) {
    resume.value.endTime = resume.value.endTime.toLocaleString('zh-CN', {
      year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit'
    }).split(' ')[0].replaceAll('/', '-').split('T')[0];
  }

  console.log(resume.value.startTime);
  console.log(resume.value.endTime);

  console.log(resume.value);

  myAxios.post(`http://localhost:8730/user/resume/update`, resume.value)
      .then(res => {
        if (res.data) {
          ElMessage({
            message: "修改成功",
            type: "success",
            duration: 2 * 1000
          })
          dialogVisible.value = false;
          resume.value = {};
          currentPage.value = 1;
          pageSize.value = 10;
          search();
        }
        else {
          ElMessage({
            message: "修改失败",
            type: "error",
            duration: 2 * 1000
          })
        }
      })
      .catch(() => {
        ElMessage({
          message: "修改失败",
          type: "error",
          duration: 2 * 1000
        })
      })
}

const handleSizeChange = (val) => {
  pageSize.value = val;
  search();
};

const handleCurrentChange = (val) => {
  currentPage.value = val;
  search();
};

const search = () => {
  myAxios.get(`http://localhost:8734/user/resume/list/${localStorage.getItem("User-Id")}/${pageSize.value}/${currentPage.value}`).then(
      res => {
        console.log(res);
        tableData.value = res.data.list;
        dataCount.value = res.data.totalCount;
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