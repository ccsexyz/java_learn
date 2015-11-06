//package com;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * @author ccsexyz
 * 主要是放一些公用的工具方法
 */
public class Tools
{
    public static final int DefaultServerPort = 9377;
    public static final String DefaultServerIP = "192.168.1.124";

    /**
     * 定义一些常用的标志
     */
    public enum Flag
    {
        LOGIN,
        SUCCESS,
        FAILED,
        USERINFO,
        PORT,
        START_CHAT,
        MESSAGE,
        REGISTER,
        SENDFILE,
        GETFILE_OK,
        GROUP_CHAT,
        PUBLIC_MESSAGE,
        SHOW_WINDOW,
        SENDIMG,
        FACE
    };

    /**
     × 将窗体居中显示
     */
    public static void setWindowsMiddleShow(JFrame frame)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().get
    }
}
