
// 检验数据是否存在
function checkData(data) {
    if (data == null || data == 'undefined' || typeof(data) == "undefined" || data.replace(/\s/g, "").length == 0) {
        return false;
    } else {
        return true;
    }
}

// 检验数据是否是数字
function checkIntData(data) {
    if (data == null || data == 'undefined' || typeof(data) == "undefined" || /^[\d]+$/.test(data) == false) {
        return false;
    } else {
        return true;
    }
}

///////////////////////////////////////////////////////////////////////////////////////
function setSessionStorage(key, val) {
    sessionStorage.setItem(key, val);
}
function getSessionStorage(key) {
    sessionStorage.getItem(key);
}
function removeSessionStorage(key) {
    sessionStorage.removeItem(key);
}

function setLocalStorage(key, val) {
    localStorage.setItem(key, val);
}
function getLocalStorage(key) {
    localStorage.getItem(key);
}
function removeLocalStorage(key) {
    localStorage.removeItem(key);
}
///////////////////////////////////////////////////////////////////////////////////////

//时间转换
function formatTIme(time) {
    var date = new Date(time);
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var hour = date.getHours();
    var minute = date.getMinutes();
    var secound = date.getSeconds();

    if (month < 10) {
        month = "0" + month;
    }
    if (day < 10) {
        day = "0" + day;
    }
    if (hour < 10) {
        hour = "0" + hour;
    }
    if (minute < 10) {
        minute = "0" + minute;
    }
    if (secound < 10) {
        secound = "0" + secound;
    }
    return  year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + secound;
}

function changePwd() {
    $("input[name='old_pwd']").val('');
    $("input[name='new_pwd']").val('');
    $("input[name='again_pwd']").val('');
    var account = getSessionStorage("account");
    if(checkData(account)){
        $("#changeAccount").val(account);
    }else{
        alert("不能获取到登录账号!");
        skipToLogin();
    }
}
function changePwdSubmit() {
    var account = $("#changeAccount").val();
    var oldPwd = $("input[name='old_pwd']").val();
    var newPwd = $("input[name='new_pwd']").val();
    var againPwd = $("input[name='again_pwd']").val();
    var is_ok = true;
    if(!checkData(account)){
        alert("不能获取到登录账号!");
        is_ok = false;
    }
    if(!checkData(oldPwd)){
        alert("老密码输入错误!");
        is_ok = false;
    }
    if(!checkData(newPwd) || !checkData(againPwd)){
        alert("新密码输入错误!");
        is_ok = false;
    }
    if(newPwd != againPwd){
        alert("两次输入密码不一致!");
        is_ok = false;
    }
    if(is_ok){
        var sid = getSid();
        $.ajax({
            type: "get",
            headers: {
                Accept: "application/json; charset=utf-8",
                Authentication: token
            },
            url: "/webUserCtrl/changePwd",
            data: {"req_sid":sid,"account":account,"oldPwd":oldPwd,"newPwd":newPwd},
            async:false,
            success: function (mes) {debugger;
                console.log(mes);
                alert(mes.body.info);
                if(mes.body.code == '200'){
                    $('#myModal').modal('hide');
                }
            }
        });
    }
}

function skipToLogin() {
    window.location.href = "../login/index.html";
}

/////////////////////////////////////////////////////////////////////////
function lay_load() {
    layer.load();
}
function lay_closeAll() {
    layer.closeAll('loading');
}
function alert(msg) {
    layer.alert(msg);
}
function lay_msg(msg) {
    layer.msg(msg);
}
////////////////////////////////////////////////////////////////////////

function formatDate(time) {
    var date = new Date(time);
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();

    if (month < 10) {
        month = "0" + month;
    }
    if (day < 10) {
        day = "0" + day;
    }

    return  year + "-" + month + "-" + day;
}

//验证日期时间格式
function checkDateTime(str) {
    var reg = new RegExp(/^\d{4}-((0?[1-9])|(1[0-2]))-((0?[1-9])|([12]\d)|(3[01])) (([01]?\d)|(2[0-3])):[0-5]\d:[0-5]\d$/);
    if(!reg.test(str)){
        return false;
    } else{
        return true;
    }
}

//退出
function loginOut(){
    var c = confirm("你确定要退出登录?");
    if(c){
        var user = localStorage.getItem("user");
        var is_ok = checkData(user);
        if(is_ok) {
            var obj = toJsonObj(user);
            var url = '/webUserCtrl/loginOut';
            var data = {"tokenId": obj.tokenId};
            var res = reqByAjaxGetAsync(url,data);
            if(res.body.code == '200'){
                window.location.href = "../login/index.html";
            }else if(res.body.code == '-1'){
                alert("系统异常");
            }
        }
    }
}

function ajaxError(jqXHR) {
    switch (jqXHR.status) {
        case 404:
            alert("资源未找到");
            break;
        case 403:
            alert("没有资源权限");
            authenPage();
            break;
        case 401:
            alert("登陆失效");
            skipToLogin();
            break;
        case 500:
            alert("系统异常");
            errorPage();
            break;
        default:
            break;
    }
}

/**
 * 系统异常跳转页面
 */
function errorPage() {
    window.location.href = "/webapp/index.html#/error";
}

/**
 * 无权限跳转页面
 */
function authenPage() {
    window.location.href = "/webapp/index.html#/authen";
}


function encode(pwd){
    if(!checkData(pwd) || pwd.length <= 4){
        return pwd;
    }
    var first_c = pwd.substring(0, 2);
    var end_c = pwd.substring(pwd.length - 2, pwd.length);
    return end_c + pwd.substring(2, pwd.length - 2) + first_c;
}