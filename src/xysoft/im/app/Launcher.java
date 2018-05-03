package xysoft.im.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import javax.swing.JFrame;
import org.apache.ibatis.session.SqlSession;
import org.jivesoftware.smack.AbstractXMPPConnection;
import xysoft.im.db.service.ContactsUserService;
import xysoft.im.db.service.CurrentUserService;
import xysoft.im.db.service.FileAttachmentService;
import xysoft.im.db.service.ImageAttachmentService;
import xysoft.im.db.service.MessageService;
import xysoft.im.db.service.RoomService;
import xysoft.im.db.service.UserService;
import xysoft.im.frames.AutoLoginFrame;
import xysoft.im.frames.LoginFrame;
import xysoft.im.frames.MainFrame;
import xysoft.im.utils.DbUtils;
import xysoft.im.utils.DebugUtil;


public class Launcher
{
    private static Launcher context;

    //############  最重要且必须设置的项  Begin ############ 
	
	public static final boolean IS_DEBUGMODE = true; // debug模式输出全部log信息
    public static final String HOSTNAME = "localhost";
    public static final String DOMAIN = "win7-1803071731";
    public static final String IP = "127.0.0.1";
    public static final String RESOURCE = "pc";    
    public static final int HOSTPORT = 5222;
    public static final int HOSTSSLPORT = 5223;
    public static final String APP_VERSION = "0.1";
    
    //############  最重要且必须设置的项  End ############ 
    
    private JFrame currentFrame;
    public static SqlSession sqlSession;
    public static RoomService roomService;
    public static UserService userService;
    public static CurrentUserService currentUserService;
    public static MessageService messageService;
    public static ContactsUserService contactsUserService;
    public static ImageAttachmentService imageAttachmentService;
    public static FileAttachmentService fileAttachmentService;
    public static  AbstractXMPPConnection connection = null;
	//来消息声音提示
	public static final boolean IS_PLAYMESSAGESOUND = true;
    public static String userHome;
    public static String appFilesBasePath;
	public static String currRoomId = "";

    public static int currentWindowWidth ;
    public static int currentWindowHeight ;
    

    static
    {
        sqlSession = DbUtils.getSqlSession();
        roomService = new RoomService(sqlSession);
        userService = new UserService(sqlSession);
        currentUserService = new CurrentUserService(sqlSession);
        messageService = new MessageService(sqlSession);
        contactsUserService = new ContactsUserService(sqlSession);
        imageAttachmentService = new ImageAttachmentService(sqlSession);
        fileAttachmentService = new FileAttachmentService(sqlSession);
		DebugUtil.debug("Service:Service初始化成功.");
    }

    public Launcher()
    {
        context = this;
    }

    public void launch()
    {
        config();

        if (!isApplicationRunning())
        {
            openFrame();
        }
        else
        {
            System.exit(-1);
        }
    }


    private void openFrame()
    {
        // TODO 原来登录过,则自动登陆
        if (checkLoginInfo())
        {
            currentFrame = new AutoLoginFrame();
        }
        // 从未登录过，未记住密码和用户名
        else
        {                	
        	login(null);
        }
        currentFrame.setVisible(true);
    }

    private void config()
    {
        userHome = System.getProperty("user.home");

        appFilesBasePath = userHome + System.getProperty("file.separator") + "xytalk";
    }

    /**
     * 检查是否有登录信息，自动登陆
     * @return
     */
    private boolean checkLoginInfo()
    {
        // TODO 判断是否已登录过的逻辑，记住密码自动登陆
        return false;
    }

    /**
     * 通过文件锁来判断程序是否正在运行
     *
     * @return 如果正在运行返回true，否则返回false
     */
    @SuppressWarnings("resource")
	private static boolean isApplicationRunning()
    {
        boolean rv = false;
        try
        {
            String path = appFilesBasePath + System.getProperty("file.separator") + "appLock";
            File dir = new File(path);
            if (!dir.exists())
            {
                dir.mkdirs();
            }

            File lockFile = new File(path + System.getProperty("file.separator") + "appLaunch.lock");
            if (!lockFile.exists())
            {
                lockFile.createNewFile();
            }

            //程序名称
            RandomAccessFile fis = new RandomAccessFile(lockFile.getAbsolutePath(), "rw");
            FileChannel fileChannel = fis.getChannel();
            FileLock fileLock = fileChannel.tryLock();
            if (fileLock == null)
            {
                System.out.println("程序已在运行.");
                rv = true;
            }
        }
        catch (FileNotFoundException e1)
        {
            e1.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return rv;
    }

    public void login(String username)
    {
    	if (username==null){
    		currentFrame = new LoginFrame();
    		currentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	}
    	else{
        
    		MainFrame.getContext().setVisible(false);
    		MainFrame.getContext().dispose();

    		currentFrame = new LoginFrame(username);
    		currentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		currentFrame.setVisible(true);
    	}
    }

    public static Launcher getContext()
    {
        return context;
    }

}