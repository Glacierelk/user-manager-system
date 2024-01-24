<template>
  <div style="margin: 20px">
    <a-form
        ref="formRef"
        :model="formState"
        class="ant-advanced-search-form"
        name="advanced_search"
        @finish="search"
    >
      <a-row :gutter="24">
        <template v-for="i in 3" :key="i">
          <a-col :span="6">
            <a-form-item
                :label=columns[i-1]
                :name=names[i-1]
            >
              <a-input v-model:value="formState[names[i-1]]"/>
            </a-form-item>
          </a-col>
        </template>
      </a-row>

      <a-row>
        <a-col :span="24" style="text-align: right">
          <a-button type="primary" :icon="h(SearchOutlined)" html-type="submit">查询</a-button>
          <a-button style="margin: 0 8px" @click="reset">清空输入</a-button>
          <a-button @click="downloadTemplate">下载导入模板</a-button>
          <a-button style="margin: 0 8px" @click="logout" danger>退出登陆</a-button>
        </a-col>
      </a-row>
    </a-form>

    <div class="search-result-list">
      <HRUserListComponent :queryForm="queryForm" />
    </div>
  </div>
</template>

<script setup>

import {h, reactive, ref} from 'vue';
import { SearchOutlined } from '@ant-design/icons-vue';
import HRUserListComponent from "@/components/HRUserListComponent.vue";
import myAxios from "@/Authorization-Axios.js";
import {ElMessage} from "element-plus";

const formRef = ref();
const formState = reactive({});
const columns = reactive(["姓名", "用户名", "电话"]);
const names = reactive(["name", "username", "phone"]);
let queryForm = ref({});

function logout() {
  localStorage.clear();
  window.location.href = '/';
}

const search = values => {
  if (values.name === undefined) values.name = 'null';
  if (values.username === undefined) values.username = 'null';
  if (values.phone === undefined) values.phone = 'null';

  console.log(values);
  queryForm.value = values;
};

const reset = () => {
  formRef.value.resetFields();
  search({
    name: undefined,
    username: undefined,
    phone: undefined
  });
};

const downloadTemplate = () => {
  myAxios({
    url: `http://localhost:8730/user/template`,
    method: 'GET',
    responseType: 'arraybuffer',
  })
      .then(response => {
        const blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' }); // 创建一个 Blob 对象
        const url = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', '用户批量注册模板.xlsx');
        document.body.appendChild(link);
        link.click();
        link.parentNode.removeChild(link);
      })
      .catch(error => {
        ElMessage.error('下载失败');
      });
}

search({
  name: undefined,
  username: undefined,
  phone: undefined
});

</script>

<style scoped>

#components-form-demo-advanced-search .ant-form {
  max-width: none;
}

#components-form-demo-advanced-search .search-result-list {
  margin-top: 16px;
  border: 1px dashed #e9e9e9;
  border-radius: 2px;
  background-color: #fafafa;
  min-height: 200px;
  text-align: center;
  padding-top: 80px;
}

[data-theme='dark'] .ant-advanced-search-form {
  background: rgba(255, 255, 255, 0.04);
  border: 1px solid #434343;
  padding: 24px;
  border-radius: 2px;
}

[data-theme='dark'] #components-form-demo-advanced-search .search-result-list {
  border: 1px dashed #434343;
  background: rgba(255, 255, 255, 0.04);
}

.search-result-list {
  margin-top: 16px;
}

</style>