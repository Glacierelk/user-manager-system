<template>
  <el-table :data="tableData" border stripe style="width: 100%">
    <el-table-column align="center" fixed label="序号">
      <template #default="{ $index }">
        <span>{{ $index + 1 }}</span>
      </template>
    </el-table-column>
    <el-table-column label="日志记录" prop="description">
      <template #default="{ row }">
        <span>{{
            row.description == null ? "无" : row.description.length <= 50 ? row.description : row.description.substring(0, 50) + "..."
          }}</span>
      </template>
    </el-table-column>
    <el-table-column align="center" label="记录时间">
      <template #default="{ row }">
        <span>{{ row.createTime }}</span>
      </template>
    </el-table-column>
    <el-table-column align="center" fixed="right" label="操作" width="230">
      <template #header>
        <el-button size="small" type="primary" @click="exportLog">导出日志
        </el-button>
      </template>
      <template #default="scope">
        <el-button plain size="small" type="primary" @click="showDetailsDialog(scope.row.description)">日志详情
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

  <el-dialog v-model="detailsDialogVisible" title="日志" width="50%">
    <div class="autowrap">
      {{ details === null ? "无" : details }}
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="detailsDialogVisible = false">关闭</el-button>
      </span>
    </template>
  </el-dialog>

</template>

<script setup>

import {ElMessageBox, ElMessage} from "element-plus";
import {defineProps, ref, watch} from 'vue';
import myAxios from '../Authorization-Axios.js'

const registerDialogVisible = ref(false);
let currentPage = ref(1);
let pageSize = ref(10);
let dataCount = ref(0);
let tableData = ref([]);
let detailsDialogVisible = ref(false);
let details = ref("");
let courseId = ref("");

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

const handleSizeChange = (val) => {
  pageSize.value = val;
  search();
};

const handleCurrentChange = (val) => {
  currentPage.value = val;
  search();
};

const showDetailsDialog = (description) => {
  details.value = description;
  detailsDialogVisible.value = true;
}

const search = () => {
  if (props.queryForm.startTime === undefined || props.queryForm.startTime === '1970-01-01')
    props.queryForm.startTime = 'null';
  if (props.queryForm.endTime === undefined || props.queryForm.endTime === '1970-01-01')
    props.queryForm.endTime = 'null';

  myAxios.get(`http://localhost:8730/logging/list/${pageSize.value}/${currentPage.value}/${props.queryForm.startTime}/${props.queryForm.endTime}`)
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

const exportLog = () => {
  myAxios({
    url: `http://localhost:8730/logging/export/${props.queryForm.startTime}/${props.queryForm.endTime}`,
    method: 'GET',
    responseType: 'arraybuffer', // 设置响应数据类型为 arraybuffer
  })
      .then(response => {
        const blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' }); // 创建一个 Blob 对象
        const url = window.URL.createObjectURL(blob); // 创建一个 URL 对象
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', '日志记录.xlsx'); // 设置下载文件名，确保是 Excel 格式（.xlsx）
        document.body.appendChild(link);
        link.click(); // 模拟点击链接进行下载
        link.parentNode.removeChild(link); // 下载完成后移除元素
      })
      .catch(error => {
        // 处理下载失败的情况
        // console.error('下载失败', error);
        ElMessage.error('下载失败');
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