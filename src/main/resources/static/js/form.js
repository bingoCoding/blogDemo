$(function() {
    var application="/blog";
    function a(a) {
        $.getJSON(application+"/admin/pro.json?id=" + a,
            function(a) {
                bootbox.dialog({
                    title: "Update Project",
                    message: '<div class="row">  <div class="col-md-12"> <form class="form-horizontal" id="add_pro_form" name="updateForm"> <input name="id" type="hidden" value="' + a.id + '">' + '<div class="form-group"> ' + '<label class="col-md-2 control-label">Name</label> ' + '<div class="col-md-9"> ' + '<input name="name" type="text" placeholder="project name" value="' + a.name + '" class="form-control input-md"> ' + "</div></div>" + '<div class="form-group"> ' + '<label class="col-md-2 control-label">Addr</label> ' + '<div class="col-md-9"> ' + '<input name="url" type="text" placeholder="project url" value="' + a.url + '" class="form-control input-md"> ' + "</div></div>" + '<div class="form-group"> ' + '<label class="col-md-2 control-label">Tech</label> ' + '<div class="col-md-9"> ' + '<textarea class="form-control" rows="3" name="tech" maxlength="220">' + a.tech + "</textarea></div></div>" + '<div class="form-group"> ' + '<label class="col-md-2 control-label">Desc</label> ' + '<div class="col-md-9"> ' + '<textarea class="form-control" rows="5" name="desp">' + a.desp + "</textarea></div></div>" + "</form> </div>  </div>",
                    buttons: {
                        success: {
                            label: "Update",
                            className: "btn-success",
                            callback: function() {
                                var a = document.getElementById("add_pro_form");
                                a.action = application+"/admin/updPro.action",
                                    a.method = "post",
                                    a.submit()
                            }
                        }
                    }
                })
            })
    }
    $("#info_update").bind("click",
        function() {
            $.post(application+"/admin/info.action",document.forms[0].serialize(),function (model) {
                alert(12);
                if(model.result==1){
                    alert("更新成功");
                }else{
                    alert("更新失败");
                }
            });
        }),
        $("#update_pass").bind("click",
            function() {
                var d, a = document.newPassForm.old_pass_bef.value,
                    b = document.newPassForm.new_pass_one.value,
                    c = document.newPassForm.new_pass_two.value;
                b != c ? alert("Two passwords are not consistent!") : (d = document.forms[1], d.action = application+"/admin/pass.action", d.method = "post", document.newPassForm.old_pass.value = md5(a), document.newPassForm.new_pass.value = md5(b), d.submit())
            }),
        $("#login-admin").bind("click",
            function() {
                var b, a = document.forms[0];
                a.action = application+"/login.action",
                    b = document.loginForm.passBefore.value,
                    document.loginForm.password.value = md5(b),
                    a.method = "post",
                    a.submit()
            }),
        $(document).on("click", "#add_project",
            function() {
                bootbox.dialog({
                    title: "New Project",
                    message: '<div class="row">  <div class="col-md-12"> <form class="form-horizontal" id="add_pro_form"> <div class="form-group"> <label class="col-md-2 control-label">Name</label> <div class="col-md-9"> <input name="name" type="text" placeholder="project name" class="form-control input-md"> </div></div><div class="form-group"> <label class="col-md-2 control-label">Addr</label> <div class="col-md-9"> <input name="url" type="text" placeholder="project url" class="form-control input-md"> </div></div><div class="form-group"> <label class="col-md-2 control-label">Tech</label> <div class="col-md-9"> <textarea class="form-control" rows="3" name="tech" maxlength="220"></textarea> </div></div><div class="form-group"> <label class="col-md-2 control-label">Desc</label> <div class="col-md-9"> <textarea class="form-control" rows="5" name="desp"></textarea> </div></div></form> </div>  </div>',
                    buttons: {
                        success: {
                            label: "Save",
                            className: "btn-success",
                            callback: function() {
                                var a = document.getElementById("add_pro_form");
                                a.action = application+"/admin/addPro.action",
                                    a.method = "post",
                                    a.submit()
                            }
                        }
                    }
                })
            }),
        $(".upd_project").click(function() {
            var b = $(this).attr("name");
            a(b)
        })
});