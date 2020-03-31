
    /*
    加载头部尾部
     */
    function load_headpage_and_footerpage() {
        $("#headpage").load("/store/head");
        $("#footerpage").load("/store/footer");

    }

    /**
     * 加载10个热门商品分类
     * ajax写法
     */
    function getCategoryTenNumber(){
        $.ajax({
            url:"/store/category/findAll",//请求的url
            dataType:"json",//返回参数类型
            async:true,//是否异步
            data:{},//请求的参数
            type:"GET",//请求方式
            beforeSend:function (data) {

            } ,//请求前的处理
            success:function (data) {
                var nav_ul = $("#category-nav-ten");
                nav_ul.empty();
                var maxnumber;
                if (data.length>10)
                    maxnumber=10;
                else maxnumber=data.length;
                for (var i = 0; i <maxnumber ; i++) {
                    var $li=$('<li><a href="/store/product/getProductsByPage?cid='+data[i].cid+'&currentPage=1&pageCount=8">'+data[i].cname+'</a></li>');
                    nav_ul.append($li);
                }

            },//请求成功的处理
            complete:function (data) {

            },//请求完成的处理
            error:function (data) {

            }//请求出错的处理
        });


    }


    // 头部简单搜索



        $("#search").click(function () {
            // alert($("#pname").val()+"---");
            location.href="http://www.worldzhile.xyz/store/product/getSearchProductsByPage?pname="+$("#pname").val()+"&pricesort=no";
        })




    // function getUrlParam(name)
    // {
    //     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    //     var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    //     if (r!=null) return unescape(r[2]); return null; //返回参数值
    // }

    function getUrlParam(name){
        // 用该属性获取页面 URL 地址从问号 (?) 开始的 URL（查询部分）
        var url = window.location.search;
        // 正则筛选地址栏
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        // 匹配目标参数
        var result = url.substr(1).match(reg);
        //返回参数值
        return result ? decodeURIComponent(result[2]) : null;
    }



