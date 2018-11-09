package com.elecredit.op.mq;

import com.alibaba.fastjson.JSON;
import com.elecredit.op.constants.EntityType;
import com.elecredit.op.constants.PermissionType;
import com.elecredit.op.model.EntityPermission;
import com.elecredit.op.model.Permission;
import com.elecredit.op.service.EntityPermissionService;
import com.elecredit.op.service.PermissionService;
import com.elecredit.user.model.Customer;
import com.rabbitmq.client.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
 * 客户新增通知处理
 */
public class CustomerAddHandler implements ChannelAwareMessageListener {
	private final Logger logger = LogManager.getLogger(CustomerAddHandler.class);
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private EntityPermissionService entityPermissionService;
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public void onMessage(Message message, Channel channel) {
		String msg = "";
		try {
			msg = new String(message.getBody(),"UTF-8");
			Customer customer = JSON.parseObject(msg,Customer.class);
			// 新增客户相关
			List<Permission> permissions = permissionService.getDefaultPermission();
			if(permissions != null){
				for(Permission permission : permissions){
					EntityPermission entityPermission = new EntityPermission();
					entityPermission.setEntityId(customer.getDefaultGroupId());
					entityPermission.setEntityType(EntityType.GROUP);
					entityPermission.setPermissionDetail("");
					entityPermission.setPermissionType(PermissionType.SYSTEM);
					entityPermission.setPermissionId(permission.getPermissionId());
					entityPermissionService.save(entityPermission);
				}
			}

			if(logger.isDebugEnabled()){
				logger.debug("消息处理成功:{}",msg);
			}
		}catch (Exception e){
            logger.error("消息处理失败:{}",msg,e);
		}finally {
			try {
				//basicAck 确认 basicNack否认
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // 消息的标识，false只确认当前一个消息收到，true确认所有consumer获得的消息
				//channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true); // ack返回false，并重新回到队列，api里面解释得很清楚
				//channel.basicReject(message.getMessageProperties().getDeliveryTag(), true); // 拒绝消息
//				System.out.println("data1:" + new String(message.getBody()));
			}catch (IOException e) {
				logger.error("消息确认失败:{}",msg,e);
			}
		}
	}
}
