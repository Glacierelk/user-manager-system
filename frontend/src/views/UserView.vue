<template>

  <div class="tab-list-div">
    <a-tabs
        v-model:activeKey="activeKey"
        type="card"
        size="large"
    >
      <a-tab-pane key="1" tab="基本信息维护">
        <InformationComponent />
      </a-tab-pane>

      <a-tab-pane key="2" tab="用户履历维护">
        <UserResumeComponent />
      </a-tab-pane>

      <template #rightExtra>
        <a-button type="primary" @click="detailsDialogVisible = true" style="margin-right: 10px">修改密码</a-button>
        <a-button @click="ElMessageBox.confirm(
            '确定要注销账户吗？',
            '提示',
            {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }
        ).then(() => {
          writtenOff();
        }).catch(() => {
        })" style="margin-right: 10px" type="dashed">注销账户</a-button>
        <a-button danger @click="logout">退出登陆</a-button>
      </template>
    </a-tabs>

  </div>

  <el-dialog v-model="detailsDialogVisible" title="修改密码" width="50%">
    <div>
      <el-input required v-model.trim="password" placeholder="请输入密码" clearable show-password type="password" />
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="success" @click="changePassword">修改</el-button>
        <el-button type="primary" @click="detailsDialogVisible = false">关闭</el-button>
      </span>
    </template>
  </el-dialog>

</template>

<script setup>

import { ref } from 'vue';
import {ElInput, ElMessage, ElMessageBox} from "element-plus";
import myAxios from "../Authorization-Axios.js";
import InformationComponent from "@/components/User/UserInformationComponent.vue";
import UserResumeComponent from "@/components/User/UserResumeComponent.vue";

const activeKey = ref('1');
const password = ref("");
const detailsDialogVisible = ref(false);

const writtenOff = () => {
  myAxios.delete(`http://localhost:8730/user/delete/${localStorage.getItem("User-Id")}`)
      .then((res) => {
        if (res.data) {
          ElMessage.success("注销成功!");
          logout();
        } else {
          ElMessage.error("注销失败!");
        }
      }).catch(() => ElMessage.error("注销失败!"));
}
function logout() {
  localStorage.clear();
  window.location.href = '/';
}

function changePassword() {
  myAxios.put(`http://localhost:8730/user/change_pwd`, {
    "id": localStorage.getItem("User-Id"),
    "password": password.value
  }).then((res) => {
    if (res.data) {
      ElMessage.success("密码修改成功!");
      detailsDialogVisible.value = false;
    } else {
      ElMessage.error("密码修改失败!");
    }
  }).catch(() => ElMessage.error("密码修改失败!"));
}

</script>

<style scoped>

.tab-list-div {
  margin-top: 20px;
  margin-left: 20px;
  margin-right: 20px;
}

</style>