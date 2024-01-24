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
        <template v-for="i in 4" :key="i">
          <a-col :span="6">
            <a-form-item
                :label=columns[i-1]
                :name=names[i-1]
                v-if="i === 1"
            >
              <a-date-picker v-model:value="formState[names[i-1]]" style="width: 100%" />
            </a-form-item>

            <a-form-item
                :label=columns[i-1]
                :name=names[i-1]
                v-if="i === 2"
            >
              <a-date-picker v-model:value="formState[names[i-1]]" style="width: 100%" />
            </a-form-item>
          </a-col>
        </template>
      </a-row>

      <a-row>
        <a-col :span="24" style="text-align: right">
          <a-button type="primary" :icon="h(SearchOutlined)" html-type="submit">查询</a-button>
          <a-button style="margin: 0 8px" @click="reset">清空输入</a-button>
          <a-button @click="logout" danger>退出登陆</a-button>
        </a-col>
      </a-row>
    </a-form>

    <div class="search-result-list">
      <AdminLoggingListComponent :queryForm="queryForm" />
    </div>
  </div>
</template>

<script setup>

import {h, reactive, ref} from 'vue';
import { SearchOutlined } from '@ant-design/icons-vue';
import AdminLoggingListComponent from "@/components/AdminLoggingListComponent.vue";

const formRef = ref();
const formState = reactive({});
const columns = reactive(["开始时间", "结束时间"]);
const names = reactive(["startTime", "endTime"]);
let queryForm = ref({});

function logout() {
  localStorage.clear();
  window.location.href = '/';
}

const search = values => {
  if (values.startTime === undefined) values.startTime = '1970-01-01';
  else values.startTime = values.startTime.format('YYYY-MM-DD');
  if (values.endTime === undefined) values.endTime = '1970-01-01';
  else values.endTime = values.endTime.format('YYYY-MM-DD');

  console.log(values);
  queryForm.value = values;
};

const reset = () => {
  formRef.value.resetFields();
  search({
    startTime: undefined,
    endTime: undefined
  });
};

search({
  startTime: undefined,
  endTime: undefined
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