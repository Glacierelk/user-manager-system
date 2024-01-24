<template>
  <div class="user-info">
    <el-form :model="userInfo" ref="userForm" label-width="100px">
      <el-form-item label="姓名">
        <el-input v-model="userInfo.name" :disabled="isReadOnly" />
      </el-form-item>
      <el-form-item label="性别">
        <el-radio-group v-model="userInfo.gender" :disabled="isReadOnly">
          <el-radio :label="0">女</el-radio>
          <el-radio :label="1">男</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="userInfo.phone" :disabled="isReadOnly" />
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="userInfo.email" :disabled="isReadOnly" />
      </el-form-item>
      <el-form-item>
        <el-button v-if="!isReadOnly" type="primary" @click="handleUpdate">修改</el-button>
        <el-button v-else @click="handleEdit">编辑</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>

import {ref} from 'vue'
import myAxios from '../../Authorization-Axios.js';
import {ElMessage} from "element-plus";

const userInfo = ref({});

const isReadOnly = ref(true);

const handleEdit = () => {
  isReadOnly.value = false;
};

const handleUpdate = () => {
  const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!regex.test(userInfo.value.email)) {
    ElMessage.error("请检查邮箱格式!");
    return;
  }
  userInfo.value.id = localStorage.getItem("User-Id");
  myAxios.post(`http://localhost:8730/user/info/update`, userInfo.value)
      .then((res) => {
        if (res.data) {
          ElMessage.success("修改成功!");
          isReadOnly.value = true;
        }
        else
          ElMessage.error("修改失败!");
      })
      .catch(() => ElMessage.error("修改失败!"));
};

const getInfo = () => {
  myAxios.get(`http://localhost:8731/user/info/get/${localStorage.getItem("User-Id")}`)
      .then((res) => {
        userInfo.value = res.data;
        ElMessage.success("用户基本信息获取成功!");
      })
      .catch(() => {
        ElMessage.error("用户基本信息获取失败!");
      })
}
getInfo();

</script>

<style scoped>

.user-info {
  max-width: 400px;
  margin: 20px auto;
}

</style>