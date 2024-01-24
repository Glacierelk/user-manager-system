<template>
  <div id="login">
    <div class="container">
      <el-form class="form-wrap" label-width="60px" @submit.native.prevent="submitForm">
        <h2 style="margin-bottom: 20px;">登录用户管理系统</h2>
        <el-form-item class="label" label="用户名" prop="username">
          <el-input v-model.trim="username" placeholder="请输入用户名" clearable required />
        </el-form-item>
        <el-form-item class="label" label="密码" prop="password">
          <el-input required v-model.trim="password" placeholder="请输入密码" clearable show-password type="password" />
        </el-form-item>
        <el-form-item>
          <el-button native-type="submit" type="success">登录</el-button>
        </el-form-item>
        <el-form-item>
          <el-button native-type="button" @click="register" type="primary">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import axios from "axios";

axios.defaults.withCredentials = false;
import {ref} from "vue";
import {ElForm, ElFormItem, ElInput, ElButton} from "element-plus";
import {useRouter} from "vue-router";

const router = useRouter();
const username = ref("");
const password = ref("");
import {ElMessage} from "element-plus";

function register() {
  if (username.value === "" || password.value === "") {

    ElMessage({
      message: '用户名或密码不能为空!',
      type: 'error',
      duration: 2 * 1000
    });
    return false;
  }

  axios.post('http://localhost:8730/user/register', {
    "username": username.value,
    "password": password.value,
  }) .then((res) => {
    if (res.data) {
      ElMessage({
        message: '注册成功, 你可以登录啦!',
        type: 'success',
        duration: 2 * 1000
      });
    } else {
      ElMessage({
        message: '注册失败，请重试!',
        type: 'error',
        duration: 2 * 1000
      });
    }
  }) .catch(() => {
    ElMessage({
      message: '注册失败，请重试!',
      type: 'error',
      duration: 2 * 1000
    });
  })
}

function submitForm() {
  console.log(username.value, password.value)

  axios.post('http://localhost:8733/user/login', {
    "username": username.value,
    "password": password.value,
  })
      .then((res) => {
        console.log(res.data)
        ElMessage({
          message: '登录成功!',
          type: 'success',
          duration: 2 * 1000
        });
        // localStorage.setItem("token", res.data.errorMsg);
        localStorage.setItem("User-Id", res.data.id);
        // 用户类型：0系统管理员；1人力资源主管；2普通用户
        switch (res.data.userType) {
          case 0:
            router.push('/admin');
            break;
          case 1:
            router.push('/hr');
            // router.push({
            //   name: 'HR',
            //   query: { executorId: res.data.data.userEmployeeId }
            // });
            break;
          case 2:
            router.push('/user')
            // router.push({
            //   name: 'instructor',
            //   query: { executorId: res.data.data.userEmployeeId }
            // });
            break;
        }
      })
      .catch((e) => {
        if (e.response) {
          if (e.response.status === 500) {
            ElMessage({
              message: '用户名不存在, 请先注册!',
              type: 'error',
              duration: 2 * 1000
            });
          } else if (e.response.status === 501) {
            ElMessage({
              message: '密码错误!',
              type: 'error',
              duration: 2 * 1000
            });
          } else {
            ElMessage({
              message: '登录失败，请重试!',
              type: 'error',
              duration: 2 * 1000
            });
          }
        } else {
          ElMessage({
            message: '登录失败，请重试!',
            type: 'error',
            duration: 2 * 1000
          });
        }
        username.value = "";
        password.value = "";
      });

  return false;
}


</script>

<style scoped>

#login {
  height: 100vh;
  width: 100%;
  background-image: url("../assets/Big_Sur_Graphic_1.jpg");
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
  overflow: hidden;
}

.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.form-wrap {
  width: 400px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
  padding: 30px;
}

h2 {
  margin-bottom: 16px;
}

.el-form-item__content > .el-input {
  width: 100%;
}

.el-button--primary,
.el-button--default {
  width: 100%;
  margin-top: 5px;
}

.el-button--primary {
  background-color: #409eff;
  border-color: #409eff;
}

.el-button--primary:hover {
  background-color: #66b1ff;
  border-color: #66b1ff;
}

.el-button--default:hover {
  background-color: #f5f5f5;
}

/* 对成功类型按钮的样式美化 */
.el-button--success {
  width: 100%;
  margin-top: 10px;
  background-color: #67c23a; /* 成功按钮背景色 */
  border-color: #67c23a; /* 成功按钮边框色 */
}

.el-button--success:hover {
  background-color: #85ce61; /* 成功按钮鼠标悬停背景色 */
  border-color: #85ce61; /* 成功按钮鼠标悬停边框色 */
}


.register {
  margin-top: 16px;
  text-align: right;
}

.label {
  font-weight: bold;
}

</style>