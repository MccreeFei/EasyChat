package cn.mccreefei.web;

import cn.mccreefei.enums.MessageTypeEnum;
import cn.mccreefei.model.Message;
import cn.mccreefei.model.MessageRecordDo;
import cn.mccreefei.model.User;
import cn.mccreefei.service.UserService;
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
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
    @Autowired
    private UserService userService;

    /**
     * 接收并且转发消息
     * @param message
     */
    @MessageMapping("/chat/message")
    public void receiveMessage(Message message){
        message.setSendDate(new Date());
        message.setMessageType("text");
        logger.info(message.getSendDate() + "," + message.getUserName() + " send a message:" + message.getContent());
        //保存聊天信息
        User userByName = userService.getUserByName(message.getUserName());
        MessageRecordDo messageRecordDo = MessageRecordDo.messageRecordBuilder()
                .userId(userByName == null ? null : userByName.getId())
                .userName(message.getUserName()).content(message.getContent())
                .messageType(MessageTypeEnum.TEXT.getCode()).createTime(new Date()).build();
        userService.addUserMessageRecord(messageRecordDo);
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
    public String handleUploadImage(HttpServletRequest request, @RequestParam("image")MultipartFile imageFile,
                                    @RequestParam("userName")String userName){
        if (!imageFile.isEmpty()){
            String imageName = userName + "_" + (int)(Math.random() * 1000000) + ".jpg";
            String path = request.getSession().getServletContext().getRealPath(IMAGE_PREFIX)  +"/" + imageName;
            File localImageFile = new File(path);
            try {
                //上传图片到目录
                byte[] bytes = imageFile.getBytes();
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(localImageFile));
                bufferedOutputStream.write(bytes);
                bufferedOutputStream.close();
                Message message = new Message();
                message.setMessageType("image");
                message.setUserName(userName);
                message.setSendDate(new Date());
                message.setContent(request.getContextPath() + IMAGE_PREFIX + imageName);

                //保存发送图片信息
                User userByName = userService.getUserByName(message.getUserName());
                MessageRecordDo messageRecordDo = MessageRecordDo.messageRecordBuilder()
                        .userId(userByName == null ? null : userByName.getId())
                        .userName(userName).content(message.getContent())
                        .messageType(MessageTypeEnum.IMAGE.getCode()).createTime(new Date()).build();
                userService.addUserMessageRecord(messageRecordDo);

                messagingTemplate.convertAndSend(SUBSCRIBE_MESSAGE_URI, message);
            } catch (IOException e) {
                logger.error("图片上传失败：" + e.getMessage(), e);
                return "upload false";
            }
        }
        return "upload success";
    }
}
