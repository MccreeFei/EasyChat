package cn.mccreefei.web;

import cn.mccreefei.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 发送消息控制器
 */
@Controller
public class MessageController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String SUBSCRIBE_MESSAGE_URI = "/topic/chat/message"; //订阅接收消息地址

    private static final String IMAGE_PREFIX = "/resources/media/image/";  //服务器储存上传图片地址的前缀

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 接收并且转发消息
     * @param message
     */
    @MessageMapping("/chat/message")
    public void receiveMessage(Message message){
        message.setSendDate(new Date());
        message.setMessageType("text");
        logger.info(message.getSendDate() + "," + message.getUserName() + " send a message:" + message.getContent());
        messagingTemplate.convertAndSend(SUBSCRIBE_MESSAGE_URI, message);
    }

    /**
     * 接收转发图片
     * @param request
     * @param imageFile
     * @param userName
     * @return
     */
    @RequestMapping(value = "/upload/image", method = RequestMethod.POST)
    @ResponseBody
    public String handleUploadImage(HttpServletRequest request, @RequestParam("image")MultipartFile imageFile, @RequestParam("userName")String userName){
        if (!imageFile.isEmpty()){
            String imageName = userName + "_" + imageFile.getOriginalFilename();
            String path = request.getServletContext().getRealPath(IMAGE_PREFIX)  + imageName;
            File localImageFile = new File(path);
            try {
                imageFile.transferTo(localImageFile);
                Message message = new Message();
                message.setMessageType("image");
                message.setUserName(userName);
                message.setSendDate(new Date());
                message.setContent(request.getContextPath() + IMAGE_PREFIX + imageName);


                messagingTemplate.convertAndSend(SUBSCRIBE_MESSAGE_URI, message);
            } catch (IOException e) {
                logger.error("图片上传失败：" + e.getMessage());
                return "upload false";
            }
        }
        return "upload success";
    }
}
