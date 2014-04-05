/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function checkform()
{
 var f=document.forms[0];
 var error='';
 error+=f.login.value==''?'\nВведіть ім’я':'';
 error+=f.pw.value==''?'\nВведіть пароль':'';
 error+=f.pw2.value==''?'\nПідтвердіть пароль':'';
 if (f.pw.value != f.pw2.value) {
     error+='\nПаролі не співпадають'+f.pw.value+' '+f.pw2.value;
 }
 if (f.mail.value == '') {
     error+='\nВведіть ваш електронний адрес';
 } else {
       error+=(f.mail.value.search("@")==-1)?'\nПеревірте правильність вашого електронного адресу':'';
 }
 
 if (error!='') {
  alert('Форма заповнена невірно:'+error);
 } else {
  f.submit();
  self.location="successful.php";
 }
}

function send(f)
{
 var chosen;
 chosen=f.options[f.selectedIndex].value;
 self.location=chosen;
}