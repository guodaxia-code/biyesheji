/**
 * 加载5商品分类轮播图
 * ajax写法
 */

var src;
var product_picture;
var arrow_l;
var arrow_r;
var value=0;
var lis;
var parentLink;

function getLunBoTu(){
    $.ajax({
        url:"http://localhost:8080/store/category/findLunBoTu",//请求的url
        dataType:"json",//返回参数类型
        async:true,//是否异步
        data:{},//请求的参数
        type:"GET",//请求方式
        beforeSend:function (data) {

        } ,//请求前的处理
        success:function (data) {
            src=data;
            console.log(src);
            console.log("----------------------")

             product_picture=document.getElementsByClassName("product-picture")[0];
            parentLink=product_picture.parentNode;
            parentLink.href='/store/product/getProductsByPage?cid='+src[0].cid+'&currentPage=1&pageCount=10';
             arrow_l=document.getElementsByClassName("arrow-l")[0];
             arrow_r=document.getElementsByClassName("arrow-r")[0];
             value=0;
            product_picture.src=src[value].picture;
             lis= document.getElementsByClassName("lunbocircle")[0].children;
            lis[value].className="current-lunbo-picture";
            arrow_l.onclick=function(){
     /*           if (value==0) {
                    value=src.length-1;
                }else{
                    value=value-1;
                }
                product_picture.src=src[value].picture;
                parentLink.href='/store/product/getProductsByPage?cid='+src[value].cid+'&currentPage=1&pageCount=10';
                for (var i = 0; i < lis.length; i++) {
                    lis[i].removeAttribute("class");
                }
                lis[value].className="current-lunbo-picture";*/
                console.log("left");
            }
            arrow_r.onclick=function(){
/*                if (value==4) {
                    value=0;
                }else{
                    value=value+1;
                }
                product_picture.src=src[value].picture;
                parentLink.href='/store/product/getProductsByPage?cid='+src[value].cid+'&currentPage=1&pageCount=10';
                for (var i = 0; i < lis.length; i++) {
                    lis[i].removeAttribute("class");
                }
                lis[value].className="current-lunbo-picture";*/
            }

            for (var i=0;i<lis.length;i++){
                lis[i].onclick=qieHuanLunBoTu;
            }


        },//请求成功的处理
        complete:function (data) {

        },//请求完成的处理
        error:function (data) {

        }//请求出错的处理
    });
}



function qieHuanLunBoTu(){
    console.log(this+"dianji")
    for (var i=0;i<lis.length;i++){
        lis[i].className="";
    }
    product_picture.src=src[this.getAttribute("index")].picture;
    this.setAttribute("class","current-lunbo-picture");
    parentLink.href='/store/product/getProductsByPage?cid='+src[this.getAttribute("index")].cid+'&currentPage=1&pageCount=10';
}