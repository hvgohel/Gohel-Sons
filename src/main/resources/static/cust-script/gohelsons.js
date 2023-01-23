
function myFunction() {
 if(document.getElementById("gender").value == 'male'){
   document.getElementById("shirtTitle").innerHTML = 'Shirt Measurement Details';
   document.getElementById("pentTitle").innerHTML = 'Pent Measurement Details';
   document.getElementById("div-crotch").style.display = 'block';
   document.getElementById("div-ankleRound").style.display = 'block';
   document.getElementById("div-knee").style.display = 'block';
   document.getElementById("div-hip").style.display = 'block';
   document.getElementById("div-seat").style.display = 'block';
   document.getElementById("div-collar").style.display = 'block';
   document.getElementById("div-kurti-waist").style.display = 'none';
   document.getElementById("div-kurti-seat").style.display = 'none';
   document.getElementById("div-front").style.display = 'block';
   document.getElementById("div-culf").style.display = 'block';
   document.getElementById("div-inseam").style.display = 'block';
   document.getElementById("div-payment").style.display = 'none';
 } else {
   document.getElementById("shirtTitle").innerHTML = 'Kurti Measurement Details';
   document.getElementById("pentTitle").innerHTML = 'Salwar Measurement Details';
   document.getElementById("div-crotch").style.display = 'none';
   document.getElementById("div-ankleRound").style.display = 'none';
   document.getElementById("div-knee").style.display = 'none';
   document.getElementById("div-hip").style.display = 'none';
   document.getElementById("div-seat").style.display = 'none';
   document.getElementById("div-collar").style.display = 'none';
   document.getElementById("div-kurti-waist").style.display = 'block';
   document.getElementById("div-kurti-seat").style.display = 'block';
   document.getElementById("div-front").style.display = 'none';
   document.getElementById("div-culf").style.display = 'none';
   document.getElementById("div-inseam").style.display = 'none';
   document.getElementById("div-payment").style.display = 'none';
 }
}

myFunction();
