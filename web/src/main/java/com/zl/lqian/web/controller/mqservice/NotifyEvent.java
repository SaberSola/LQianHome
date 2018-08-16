
package com.zl.lqian.web.controller.mqservice;

/**
 * @author zl on 2015/8/31.
 */

public class NotifyEvent {
	private static final long serialVersionUID = -4261382494171476390L;
	
	private long fromUserId;
    private long toUserId;
    private int event;
    private long postId;


    public long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public long getToUserId() {
        return toUserId;
    }

    public void setToUserId(long toUserId) {
        this.toUserId = toUserId;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }
}
