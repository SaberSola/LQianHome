/**
 *
 */
package com.zl.lqian.template.directive;

import com.zl.lqian.template.DirectiveHandler;
import com.zl.lqian.template.TemplateDirective;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 资源路径处理
 * - 当 ${resource.domain} = true 时，自己在资源地址前面追加host
 * @author zl
 *
 */
@Component
public class ResourceDirective extends TemplateDirective {
    @Override
    public String getName() {
        return "resource";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        String src = handler.getString("src", "#");
        String base = handler.getString("base");

        if (StringUtils.isBlank(base)) {
            base = handler.getContextPath();
        }

        StringBuffer buf = new StringBuffer();

        buf.append(base);
        buf.append(src);

        handler.renderString(buf.toString());
    }

}
