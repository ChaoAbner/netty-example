package com.fosuchao.websocket.config;

/**
 * @Description:
 * @Auther: Joker Ye
 * @Date: 2020/2/13 12:59
 */
public class NettyConfig {
    private String protocol = "websocket";

    public static class WebSocket {
        private static int PORT = 8899;
        private static String PATH = "/chat";

        public static int getPORT() {
            return PORT;
        }

        public static String getPATH() {
            return PATH;
        }

        public static class HttpObjectArrgregator {
            /**
             * MAX_LENGTH: 内容最大长度
             */
            private static int maxLength = 64 * 1024;

            public static int getMaxLength() {
                return maxLength;
            }
        }
    }
}
