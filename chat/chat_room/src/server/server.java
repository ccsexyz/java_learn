package server;

import com.Bean;
import com.clientBean;
import com.beanType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Created by Johnson on 2015/11/7.
 */
public class server {
    private static ServerSocket ss;
    public static HashMap<String, clientBean> onlines;
    static {
        try {
            ss = new ServerSocket(9377);
            onlines = new HashMap<String, clientBean>();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    class clientThread extends Thread {
        private Socket client;
        private Bean bean;
        private ObjectInputStream ois;
        private ObjectOutputStream oos;

        public clientThread(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                while(true) {
                    ois = new ObjectInputStream(client.getInputStream());
                    System.out.println("try to read!\n");
                    bean = (Bean)ois.readObject();
                    System.out.println("read sth!\n");

                    switch (bean.getType()) {
                        case message: {
                            Bean serverBean = new Bean();
                            System.out.println("get message!\n");

                            serverBean.setType(beanType.message);
                            //serverBean.setClients(bean.getClients());
                            HashSet<String> set = new HashSet<String>();
                            set.addAll(onlines.keySet());
                            serverBean.setClients(set);
                            serverBean.setInfo(bean.getInfo());
                            serverBean.setName(bean.getName());
                            serverBean.setTimer(bean.getTimer());

                            sendAll(serverBean);
                            break;
                        }
                        case login: {
                            clientBean cbean = new clientBean();
                            cbean.setName(bean.getName());
                            cbean.setSocket(client);
                            System.out.println("get login!\n");

                            onlines.put(bean.getName(), cbean);

                            Bean serverBean = new Bean();
                            serverBean.setType(beanType.login);
                            serverBean.setInfo(bean.getTimer() + " " + bean.getName() + "上线了");

                            HashSet<String> set = new HashSet<String>();

                            set.addAll(onlines.keySet());
                            serverBean.setClients(set);
                            sendAll(serverBean);
                            break;
                        }
                        case offline: {
                            Bean serverBean = new Bean();
                            serverBean.setType(beanType.offline);

                            try {
                                oos = new ObjectOutputStream(client.getOutputStream());
                                oos.writeObject(serverBean);
                                oos.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            onlines.remove(bean.getName());

                            Bean serverBean_ = new Bean();
                            serverBean_.setInfo(bean.getTimer() + " " + bean.getName() + " " + " 下线了");
                            serverBean_.setType(beanType.login);
                            HashSet<String> set = new HashSet<String>();
                            set.addAll(onlines.keySet());
                            serverBean_.setClients(set);

                            sendAll(serverBean_);

                            break;
                        }
                        case trySendFile: {
                            Bean serverBean = new Bean();
                            String info = bean.getTimer() + " " + bean.getName() + "向你传送文件,是否接受";

                            serverBean.setType(beanType.trySendFile);
                            serverBean.setClients(bean.getClients());
                            serverBean.setFileName(bean.getFileName());
                            serverBean.setSize(bean.getSize());
                            serverBean.setInfo(info);
                            serverBean.setName(bean.getName());
                            serverBean.setTimer(bean.getTimer());

                            sendAll(serverBean);

                            break;
                        }
                        case toRecvFile: {
                            Bean serverBean = new Bean();

                            serverBean.setType(beanType.toRecvFile);
                            serverBean.setClients(bean.getClients());
                            serverBean.setTo(bean.getTo());
                            serverBean.setFileName(bean.getFileName());
                            serverBean.setIp(bean.getIp());
                            serverBean.setPort(bean.getPort());
                            serverBean.setName(bean.getName());
                            serverBean.setTimer(bean.getTimer());

                            sendAll(serverBean);

                            break;
                        }
                        default: {
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }

        public void sendAll(Bean serverBean) {
            Collection<clientBean> clients = onlines.values();
            Iterator<clientBean> it = clients.iterator();
            ObjectOutputStream oos;
            while(it.hasNext()) {
                Socket c = it.next().getSocket();
                try {
                    oos = new ObjectOutputStream(c.getOutputStream());
                    oos.writeObject(serverBean);
                    oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void sendMessage(Bean serverBean) {
            Set<String> cbs = onlines.keySet();
            Iterator<String> it = cbs.iterator();

            HashSet<String> clients = serverBean.getClients();
            while(it.hasNext()) {
                String client = it.next();
                if(clients.contains(client)) {
                    Socket c = onlines.get(client).getSocket();
                    ObjectOutputStream oos;
                    try {
                        oos = new ObjectOutputStream(c.getOutputStream());
                        oos.writeObject(serverBean);
                        oos.flush();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private void close() {
            if(oos != null) {
                try {
                    oos.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }

            if(ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void start() {
        try {
            while(true) {
                Socket client = ss.accept();
                new clientThread(client).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("start!\n");
        new server().start();
    }
}
