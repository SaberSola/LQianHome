<#include "/default/utils/ui.ftl"/>

<@layout "关于我们">

<div class="row main">
    <div class="col-xs-12 col-md-9 side-left topics-show">
        <!-- view show -->
        <div class="topic panel panel-default">
            <div class="infos panel-heading">

                <h1 class="panel-title topic-title">关于我们</h1>

                <div class="meta inline-block">
                    <a class="author" href="#">官方团队</a>
                </div>
                <div class="clearfix"></div>
            </div>

            <div class="content-body entry-content panel-body ">
                <div class="markdown-body" id="emojify">
                    <p><strong>关于LQianHome</strong><br/></p>
                    <p>关于LQianHome是一个借鉴LQianHome的一个私人网站,建立的目的只是为了想记录我和我老婆的点点滴滴,以及分享自己写代码的
                    一些理解与心得</p>
                    <p><strong>知识产权</strong><br/></p>
                    <p>网站内容来自用户自行发起，或转载于互联网，如有涉及到版权问题，请告诉我们，我们将会对其进行删除处理</p>
                    <p><strong>因为我实在太懒了,所以前端代码完全借鉴,后台代码本人会修改</strong></p>
                    <p>代码托管在GitHub <a href="https://github.com/SaberSola/LQianHome" target="_blank">https://github.com/SaberSola/LQianHome</a></p>

				</div>
            </div>
            <div class="panel-footer operate">
            </div>
            <div class="panel-footer operate">
                <div class="hidden-xs">
                    <div class="social-share" data-sites="weibo, wechat, facebook, twitter, google, qzone, qq"></div>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>
        <!-- /view show -->
    </div>
    <div class="col-xs-12 col-md-3 side-right hidden-xs hidden-sm">
		<#include "/default/inc/right.ftl"/>
    </div>
</div>

</@layout>