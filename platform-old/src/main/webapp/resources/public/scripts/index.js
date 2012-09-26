var app={name:''};
var _loadingICO;
var account={};
var allNavigation=[];
$(function() {
	_loadingICO=app.name+'/resources/public/images/loading.gif';
	$('body').append(unescape("%3Cscript src='"+app.name+"/resources/public/scripts/XYTipsWindow-3.0.js' type='text/javascript'%3E%3C/script%3E"));
	$('#reg').click(function(){
		regWin();
	});
	$('#login').click(function(){
		loginWin();
	});
	$('#logout').click(function(){
		$.getJSON(app.name+'/portal/account/logout', function(data) {
			if (data==1) {
				$('#loginBefore').show();
				$('#lolginAfter').hide();
				$('#welcome').empty().attr('userId','');
				account={};
				location.reload();
			}
		});
	});
	$('#welcome').click(profileTip);
	$('#thumb_photo').click(profileTip);
	$('#gbi5').click(function () {
		favorite();
    });
});

var login=function(){
	$('#loginForm').form({
		url:app.name+'/portal/account/login',
		dataType : 'text',  
		onSubmit:function(){
			var tag=true;
			$('#loginForm span[id$="Tip"]').each(function(){
				if(!$(this).hasClass('onCorrect')&&!$(this).hasClass('onSuccess')){
					tag=false;
					$('#'+$(this).attr('id').substring(0, $(this).attr('id').indexOf('Tip'))).blur();
					return false;
				}
			});
			return tag;
		}, 
		success:function(data){
			if(data){
				var map=$.parseJSON(data);
				if(map){
					if(map.answerCode==-3){
						tips('password','密码错误次数超过限制，半小时内限制登录','bottom');
					}
					if(map.answerCode==-2){
						tips('email','账号被锁定','bottom');
					}
					if(map.answerCode==-1){
						tips('email','账号不存在','bottom');
					}
					if(map.answerCode==0){
						tips('password','密码错误','bottom');
					}
					if(map.answerCode==1){
						account=map.portalAccount;
						/*
						$('#loginBefore').hide();
						$('#lolginAfter').show();
						$('#welcome').html(account.nickName);
						$('#thumb_photo').click(profileTip);
						if(account.thumbPhoto){
							$('#thumb_photo').css({
								'background':'url('+account.thumbPhoto+')',
								'background-position':''
							});
						} else {
							$('#thumb_photo').css({
								'background':'url('+app.name+'/resources/public/images/h_bedf916a.png)',
								'background-position':'-98px -172px'
							});
						}*/
						Util.Dialog.remove('loginWin');
						Util.Dialog.remove('Tip_tips');
						location.reload();
					}
				}else{
					tips('button','后台系统异','bottom');
				}
			}else{
				tips('button','后台系统异','bottom');
			}
		}
	}).submit();
};
var reg=function(){
	$('#regForm').form({
		url:app.name+'/portal/account/register',
		dataType : 'text',  
		onSubmit:function(){
			var tag=true;
			$('#regForm span[id$="Tip"]').each(function(){
				if(!$(this).hasClass('onCorrect')&&!$(this).hasClass('onSuccess')){
					tag=false;
					$('#'+$(this).attr('id').substring(0, $(this).attr('id').indexOf('Tip'))).blur();
					return false;
				}
			});
			return tag;
	    }, 
    	success:function(data){
			if(data){
				var map=$.parseJSON(data);
				if(map){
					if(map.answerCode==-2){
						tips('button','未知错误','bottom');
					}
					if(map.answerCode==1){
						//tips('button','注册成功','bottom');
						Util.Dialog.remove('regWin');
						location.reload();
					}
				}else{
					tips('button','后台系统异','bottom');
				}
			}else{
				tips('button','后台系统异','bottom');
			}
	    }
	}).submit();
};

var regWin=function(){
	Util.Dialog({
		boxID : 'regWin',
		title : '用户注册',
		content : 'url:get?'+app.name+'/html/reg.html',
		showbg: true,
		width : 600,
		height : 280
	});
	return false;
};
var loginWin=function(){
	Util.Dialog({
		boxID : 'loginWin',
		title : '用户登录',
		content : 'url:get?'+app.name+'/html/login.html',
		showbg: true,
		width : 480,
		height : 250
	});
	return false;
};
var accountWin=function(){
	Util.Dialog.remove("profile_tips");
	Util.Dialog({
		boxID : 'accountWin',
		title : '用户中心',
		content : 'url:get?'+app.name+'/html/account.html',
		showbg: true,
		width : 580,
		height : 420
	});
	return false;
};
var chPwdWin=function(){
	Util.Dialog.remove("profile_tips");
	Util.Dialog({
		boxID : 'chPwdWin',
		title : '修改密码',
		content : 'url:get?'+app.name+'/html/changePwd.html',
		showbg: true,
		width : 600,
		height : 235
	});
	return false;
};

var profileTip=function(){
	Util.Dialog({
		type: 'tips',
		boxID: 'profile_tips',
		referID: 'thumb_photo',
		width: 250,
		height: 106,
		border: { opacity: '0', radius: '3'},
		closestyle: 'gray',
		arrow: 'bottom',
		fixed: false,
		time: 5000,
		arrowset: {val: '130px'},
		content: 'text:<div id="profile"><img src="'+account.photo+'" /><div><span>'+account.nickName+'</span><span id="profile_email">'+account.email+'</span><span class="operater" onclick="accountWin();">个人资料</span><span class="operater" onclick="chPwdWin();">修改密码</span></div></div>',
		position: { 
			left: '-190px', 
			top: '-50px',
			lin: true,
			tin: false
		}
	});
};
var favorite=function(){
	Util.Dialog({
		boxID: 'fav_tips',
		referID: 'gbi5',
		width: 150,
		//height: 450,
		border: { opacity: '0', radius: '3'},
		closestyle: 'gray',
		arrow: 'botton',
		fixed: true,
		//time: 5000,
		arrowset: {val: '30px'},
		content : 'url:get?'+app.name+'/html/favorite.html?'+new Date().getTime(),
		position: { 
			left: '-70px', 
			top: '5px',
			lin: true,
			tin: false
		}
	});
	return false;
};
var tips=function(ref,txt,_arrow){
	Util.Dialog({
		type: 'tips',
		boxID: 'Tip_tips',
		referID: ref,
		//width: 150,
		height: 20,
		border: { opacity: '0', radius: '3'},
		closestyle: 'gray',
		arrow: _arrow,
		fixed: false,
		time: 3000,
		arrowset: {val: '10px'},
		content: 'text:'+txt,
		position: { 
			left: '-20px', 
			top: '-50px',
			lin: true,
			tin: false
		}
	});
};