package kafak;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.bwie.utils.DateUtils;
import com.bwie.utils.RandomUtils;
import com.bwie.utils.StreamUtils;
import com.lijianan.cms.domain.ArticleWithBLOBs;
import com.lijianan.cms.domain.Category;
import com.lijianan.cms.domain.Channel;
import com.lijianan.cms.service.CategoryService;
import com.lijianan.cms.service.ChannelService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-beans.xml")
public class ProducerTest {

		@Resource
		private KafkaTemplate<String, String> kafkaTemplate;
		

		@Resource
		private ChannelService channelService;
		
		@Resource
		private CategoryService categoryService;
		@Test
		public void testSendMsg() throws FileNotFoundException {
			//读取文章
			//文件夹
			File dir = new File("E:\\1707EJsoup");
			
			//获取文件的数组
			File[] listFiles = dir.listFiles();
			
			//循环遍历
			for (File file : listFiles) {
				//读取file数据，获取文章内容
				String content = StreamUtils.readTextFile(file);
				
				//获取标题
				String name = file.getName().replace(".txt", "");
				
				//封装数据
				ArticleWithBLOBs article = new ArticleWithBLOBs();
				
				article.setTitle(name);
				article.setContent(content);
				
//				(4)在文本内容中截取前140个字作为摘要。（2分）
				String summary = content;
				if(content.length() > 140) {
					summary = content.substring(0, 140);
				}
				
				article.setSummary(summary);
				
//				(5)“点击量”和“是否热门”、“频道”字段要使用随机值。（2分）
				//点击量
				article.setHits(RandomUtils.random(0, 10000));
				
				//是否热门
				article.setHot(RandomUtils.random(0, 1));
				
				//栏目
				//获取所有的栏目，存入list中
				List<Channel> channels = channelService.selects();
				//随机获取一个下标
				int random = RandomUtils.random(0, channels.size() - 1);
				//再根据下标取出对应的值
				Channel channel = channels.get(random);
				
				article.setChannelId(channel.getId());
				
				//类别
				//获取指定栏目下的类别
				List<Category> categories = categoryService.selectByChannelId(channel.getId());
				
				if(categories != null ) {
					
					//随机下标
					int random2 = RandomUtils.random(0, categories.size() - 1);
					
					//获取类别的id
					Category category = categories.get(random2);
					
					article.setCategoryId(category.getId());
				}
				
//				(6)文章发布日期从2019年1月1日模拟到今天。（2分）   -2
				
//				article.setCreated(DateUtil.randomDate(new Date("2019/01/01"), new Date()));
				
				article.setCreated(DateUtils.randomDate("2019-01-01", "2019-12-18"));
				
//				(7)其它的字段随便模拟。
				//状态
				article.setStatus(0);
				//删除
				article.setDeleted(0);
				//类型
				article.setContentType(0);
				
				System.out.println(article);
				
				//转换成json字符串
				String json = JSON.toJSONString(article);
				
				//发送到kafka
				kafkaTemplate.sendDefault("article_add", json);
			}
		}
}
