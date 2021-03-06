package xysoft.im.frames;

import xysoft.im.components.*;
import xysoft.im.panels.*;
import xysoft.im.utils.FontUtil;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class SystemConfigDialog extends JDialog
{
    /**
	 * 系统配置UI，内容区使用cardLayout切换
	 */
	private static final long serialVersionUID = -8589252870208818272L;
	private static SystemConfigDialog context;
    private JPanel buttonPanel;
    //private JButton cancelButton;
    private JButton okButton;

    private JPanel settingPanel;
    private JPanel settingMenuPanel;
    private JPanel settingAreaPanel;
    private JLabel changeAvatarLabel;
    //private JLabel changeVcardLabel;
    //private JLabel changePasswordLabel;

    private JLabel meLabel;
    private JLabel aboutLabel;
    private JLabel clearCacheLabel;
    private JLabel clearDBLabel;
    private JLabel syncOrgLabel;
    private JLabel configLabel;

    private ChangeAvatarPanel changeAvatarPanel;
    //private ChangePasswordPanel changePasswordPanel;
    //private ChangeVcardPanel changeVcardPanel;
    private MePanel mePanel;
    private AboutPanel aboutPanel;
    private ClearCachePanel clearCachePanel;
    private ClearDBPanel clearDBPanel;
    private SyncOrgPanel syncOrgPanel;
    private ConfigPanel configPanel;

    private JLabel selectedLabel;

    public static final String CHANGE_AVATAR = "CHANGE_AVATAR";
    public static final String CHANGE_PASSWORD = "CHANGE_PASSWORD";
    public static final String CHANGE_SIGN = "CHANGE_SIGN";
    
    public static final String ME = "ME";
    public static final String ABOUT = "ABOUT";
    public static final String CLEAR_CHACE = "CLEAR_CHACE";
    public static final String CLEAR_DB = "CLEAR_DB";
    public static final String SYNC_DB = "SYNC_DB";
    public static final String CFG_SYS = "CFG_SYS";
    
    
    

    private CardLayout cardLayout = new CardLayout();


    public static final int DIALOG_WIDTH = 580;
    public static final int DIALOG_HEIGHT = 500;
    private Cursor handCursor;


    public SystemConfigDialog(Frame owner, boolean modal)
    {
        super(owner, modal);
        context = this;

        initComponents();
        initData();

        initView();
        setListeners();
    }

    private void initData()
    {

    }


    private void initComponents()
    {
        int posX = MainFrame.getContext().getX();
        int posY = MainFrame.getContext().getY();

        posX = posX + (MainFrame.getContext().currentWindowWidth - DIALOG_WIDTH) / 2;
        posY = posY + (MainFrame.getContext().currentWindowHeight - DIALOG_HEIGHT) / 2;
        setBounds(posX, posY, DIALOG_WIDTH, DIALOG_HEIGHT);
        setUndecorated(true);

        getRootPane().setBorder(new LineBorder(Colors.DIALOG_BORDER));

        // 按钮组
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        /*cancelButton = new RCButton("取消");
        cancelButton.setForeground(Colors.FONT_BLACK);*/
        okButton = new RCButton("关闭", Colors.MAIN_COLOR, Colors.MAIN_COLOR_DARKER, Colors.MAIN_COLOR_DARKER);
        okButton.setPreferredSize(new Dimension(75, 30));

        // 设置面板
        settingPanel = new JPanel();

        settingMenuPanel = new JPanel();
        settingAreaPanel = new JPanel();
        settingAreaPanel.setBorder(new RCBorder(RCBorder.LEFT, Colors.SCROLL_BAR_TRACK_LIGHT));


        handCursor = new Cursor(Cursor.HAND_CURSOR);

        // 设置头像按钮
        changeAvatarLabel = new JLabel("更改头像");
        processButtonLabel(changeAvatarLabel);

        //changeVcardLabel = new JLabel("心情签名");
        //processButtonLabel(changeVcardLabel);
        
        // 更改密码按钮
        //changePasswordLabel = new JLabel("修改密码");
        //processButtonLabel(changePasswordLabel);

        // "我" 按钮
        meLabel = new JLabel("我");
        processButtonLabel(meLabel);

        // 关于 按钮
        aboutLabel = new JLabel("关于");
        processButtonLabel(aboutLabel);

        // 清除缓存 按钮
        clearCacheLabel = new JLabel("清除缓存");
        processButtonLabel(clearCacheLabel);

        // 清除缓存 按钮
        clearDBLabel = new JLabel("清除数据库");
        processButtonLabel(clearDBLabel);
        
        syncOrgLabel = new JLabel("同步联系人");
        processButtonLabel(syncOrgLabel);

        configLabel = new JLabel("设置");
        processButtonLabel(configLabel);
        // 更改头像面板
        changeAvatarPanel = new ChangeAvatarPanel();

        // 更改密码面板
        //changePasswordPanel = new ChangePasswordPanel();
        
        // 更改心情签名等用户资料
        //changeVcardPanel = new ChangeVcardPanel();

        // "我" 面板
        mePanel = new MePanel();

        // 关于面板
        aboutPanel = new AboutPanel();
        
        // 清除数据库面板
        clearDBPanel = new ClearDBPanel();
        // 清除缓存面板
        clearCachePanel = new ClearCachePanel();
        // 同步联系人组织架构面板
        syncOrgPanel = new SyncOrgPanel();
        
        configPanel = new ConfigPanel();
        

    }


    private void initView()
    {
        //buttonPanel.add(cancelButton, new GBC(0, 0).setWeight(1, 1).setInsets(15, 0, 0, 0));
        buttonPanel.add(okButton, new GBC(1, 0).setWeight(1, 1));

        settingPanel.setLayout(new GridBagLayout());
        settingPanel.add(settingMenuPanel, new GBC(0, 0).setWeight(1, 1).setFill(GBC.BOTH).setInsets(10,0,0,0));
        settingPanel.add(settingAreaPanel, new GBC(1, 0).setWeight(6, 1).setFill(GBC.BOTH).setInsets(10,0,0,0));

        settingMenuPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP, 0, 0, true, false));
        settingMenuPanel.add(meLabel);
        settingMenuPanel.add(configLabel);       
        settingMenuPanel.add(changeAvatarLabel);
        //settingMenuPanel.add(changePasswordLabel);       
        //settingMenuPanel.add(changeVcardLabel);
        settingMenuPanel.add(clearDBLabel);
        settingMenuPanel.add(clearCacheLabel);
        settingMenuPanel.add(syncOrgLabel);
        settingMenuPanel.add(aboutLabel);

        settingAreaPanel.setLayout(cardLayout);
        settingAreaPanel.add(mePanel, ME);
        settingAreaPanel.add(changeAvatarPanel, CHANGE_AVATAR);
//        settingAreaPanel.add(changePasswordPanel, CHANGE_PASSWORD);
        //settingAreaPanel.add(changeVcardPanel, CHANGE_SIGN);
        
        settingAreaPanel.add(aboutPanel, ABOUT);
        settingAreaPanel.add(clearDBPanel, CLEAR_DB);
        settingAreaPanel.add(clearCachePanel, CLEAR_CHACE);
        settingAreaPanel.add(syncOrgPanel, SYNC_DB);
        settingAreaPanel.add(configPanel, CFG_SYS);
        
        add(settingPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        selectedLabel(meLabel);
    }

    private void setListeners()
    {
        okButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                setVisible(false);

                super.mouseClicked(e);
            }
        });

        MouseAdapter itemMouseListener = new MouseAdapter()
        {
           
            public void mouseEntered(MouseEvent e)
            {
                JLabel source = ((JLabel) e.getSource());
                if (source != selectedLabel)
                {
                    source.setBackground(Colors.ITEM_SELECTED_LIGHT);
                }
                super.mouseEntered(e);
            }

            public void mouseExited(MouseEvent e)
            {
                JLabel source = ((JLabel) e.getSource());
                if (source != selectedLabel)
                {
                    source.setBackground(Colors.WINDOW_BACKGROUND);
                }
                super.mouseExited(e);
            }

            public void mouseClicked(MouseEvent e)
            {
                JLabel source = ((JLabel) e.getSource());

                if (source != selectedLabel)
                {
                    selectedLabel(source);

                    if (source.getText().equals("更改头像"))
                    {
                        cardLayout.show(settingAreaPanel, CHANGE_AVATAR);
                    }
                    else if (source.getText().equals("修改密码"))
                    {
                        cardLayout.show(settingAreaPanel, CHANGE_PASSWORD);
                    }
                    else if (source.getText().equals("心情签名"))
                    {
                        cardLayout.show(settingAreaPanel, CHANGE_SIGN);
                    }
                    else if (source.getText().equals("我"))
                    {
                        cardLayout.show(settingAreaPanel, ME);
                    }
                    else if (source.getText().equals("关于"))
                    {
                        cardLayout.show(settingAreaPanel, ABOUT);
                    }
                    else if (source.getText().equals("清除缓存"))
                    {
                        cardLayout.show(settingAreaPanel, CLEAR_CHACE);
                    }
                    else if (source.getText().equals("清除数据库"))
                    {
                        cardLayout.show(settingAreaPanel, CLEAR_DB);
                    }
                    else if (source.getText().equals("同步联系人"))
                    {
                        cardLayout.show(settingAreaPanel, SYNC_DB);
                    }
                    else if (source.getText().equals("设置"))
                    {
                        cardLayout.show(settingAreaPanel, CFG_SYS);
                    }
                    
                    
                }


                super.mouseClicked(e);
            }
        };

        changeAvatarLabel.addMouseListener(itemMouseListener);
        //changeVcardLabel.addMouseListener(itemMouseListener);
        //changePasswordLabel.addMouseListener(itemMouseListener);
        meLabel.addMouseListener(itemMouseListener);
        aboutLabel.addMouseListener(itemMouseListener);
        clearCacheLabel.addMouseListener(itemMouseListener);
        clearDBLabel.addMouseListener(itemMouseListener);
        syncOrgLabel.addMouseListener(itemMouseListener);
        configLabel.addMouseListener(itemMouseListener);
    }

    private void selectedLabel(JLabel label)
    {
        selectedLabel = label;

        for (Component component : settingMenuPanel.getComponents())
        {
            component.setBackground(Colors.WINDOW_BACKGROUND);
        }

        label.setBackground(Colors.SCROLL_BAR_TRACK_LIGHT);
    }


    public static SystemConfigDialog getContext()
    {
        return context;
    }

    private void processButtonLabel(JLabel label)
    {
        label.setFont(FontUtil.getDefaultFont(13));
        label.setForeground(Colors.DARKER);
        label.setBorder(new RCBorder(RCBorder.BOTTOM, Colors.SHADOW));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(50, 30));
        label.setCursor(handCursor);
        label.setOpaque(true);
    }
}
