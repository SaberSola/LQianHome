/*
+--------------------------------------------------------------------------
|   LQianHome [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 LQianHome. All Rights Reserved
|   http://www.LQianHome.com
|
+---------------------------------------------------------------------------
*/

define(function(require, exports, module) {
    //require.async('ueditor.parse', function () {
    //    uParse('.post-content',{
    //        rootPath : window.UEDITOR_HOME_URL
    //    })
    //});

    require.async('highlight', function () {
        hljs.initHighlightingOnLoad();
        $('pre').each(function(i, block) {
            hljs.highlightBlock(block);
        });
    });

    require.async('share', function () {
    });

});