<#include "/default/utils/ui.ftl"/>

<@layout "联系楼主">

<div class="row main">
    <div class="col-xs-12 col-md-9 side-left topics-show">
        <!-- view show -->
        <div class="topic panel panel-default">
            <div class="infos panel-heading">

                <h1 class="panel-title topic-title">联系楼主</h1>

                <div class="meta inline-block">
                    <a class="author" href="https://github.com/SaberSola/" target="_blank">楼主github地址</a>
                </div>
                <div class="clearfix"></div>
            </div>

            <div class="content-body entry-content panel-body ">
                <div class="markdown-body" id="emojify">
                    <p><strong>关于网站</strong></p>
                    <p>此代码完全开源github地址:<a href="https://github.com/SaberSola/LQianHome" target="_blank">https://github.com/SaberSola/LQianHome</a></p>
                    <p><strong>联系楼主</strong><br/></p>
                    <p>此网站不会有任何商业性质,纯粹是个人网站,要是您有任何问题请联系楼主</p>
                    <p>微信：13162706810</p>
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