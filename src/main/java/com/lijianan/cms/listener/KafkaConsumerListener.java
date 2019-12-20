package com.lijianan.cms.listener;

import javax.annotation.Resource;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.lijianan.cms.domain.ArticleWithBLOBs;
import com.lijianan.cms.service.ArticleService;







@Component
public class KafkaConsumerListener implements MessageListener<String, String>{
	
	@Resource
	private ArticleService articleService;

	@Override
	public void onMessage(ConsumerRecord<String, String> record) {
		String key = record.key();
		
		//添加数据
		if(key != null && key.equals("article_add")) {
			String value = record.value();
			
			//转换成对象
			ArticleWithBLOBs article = JSON.parseObject(value, ArticleWithBLOBs.class);
			
			//存入mysql数据库
			articleService.insertSelective(article);
		}
		
	}

}
