import javax.swing.*;
import java.awt.*;

public class GameGUI extends JFrame {

    private JPanel gamePanel;
    private CardLayout gameCardLayout;
    private Hero[] myTeam;
    private Hero[] enemyTeam;
    private boolean isPlayerTurn = true;
    private boolean isAi = true;
    private int count = 0;

    public GameGUI() {
       
        myTeam = new Hero[3];
        enemyTeam = new Hero[3];
        
        
        gameCardLayout = new CardLayout();
        gamePanel = new JPanel(gameCardLayout);

        setTitle("Shadows of Eldora");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        
        JPanel mainScreen = createMainScreen();
        JPanel gameModeScreen = createGameModeScreen();
        JPanel playScreen = createPlayScreen();
        JPanel selectionScreen = createSelectionScreen();

       
        gamePanel.add(mainScreen, "MainScreen");
        gamePanel.add(gameModeScreen, "GameMode");
        gamePanel.add(playScreen, "PlayScreen");
        gamePanel.add(selectionScreen, "SelectionScreen");

        // --- Add Main Panel ---
        add(gamePanel);
        gameCardLayout.show(gamePanel, "MainScreen");

        setVisible(true);
    }

    
    private JPanel createPlayScreen() {
        JPanel playScreen = new JPanel(new BorderLayout());
        playScreen.setBackground(new Color(40, 40, 60));

        JPanel playIntro = new JPanel(new BorderLayout());
        playIntro.setBackground(new Color(40, 40, 60));

        JTextArea playStory = new JTextArea(
            "Long ago, the peaceful village of Eldonia was destroyed by the Overlord of Shadows, " +
            "leaving five childhood friends as its only survivors. Adrian the Warrior, Jhush the " +
            "Assassin, Cyberg the Mage, Rex the Archer, and Clarence the Tank swore a blood oath " +
            "to fight as brothers and avenge their fallen home.\n\n" +
            "Each trained in their own discipline: strength, speed, magic, precision, and defense, " +
            "becoming the last sons of Eldonia. United by brotherhood and destiny, they now battle " +
            "together to restore light to the kingdom."
        );
        playStory.setForeground(Color.WHITE);
        playStory.setFont(new Font("Serif", Font.PLAIN, 18));
        playStory.setBackground(new Color(40, 40, 60));
        playStory.setEditable(false);
        playStory.setFocusable(false);
        playStory.setLineWrap(true);
        playStory.setWrapStyleWord(true);
        playStory.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JScrollPane scrollPane = new JScrollPane(playStory);
        scrollPane.setBorder(null);
        scrollPane.setBackground(new Color(40, 40, 60));
        scrollPane.getViewport().setBackground(new Color(40, 40, 60));
        playIntro.add(scrollPane, BorderLayout.CENTER);

        JPanel playButtonPanel = new JPanel();
        playButtonPanel.setBackground(new Color(40, 40, 60));

        JButton playContinue = new JButton("CONTINUE");
        playContinue.setFont(new Font("Serif", Font.BOLD, 32));
        playContinue.setForeground(Color.WHITE);
        styleButton(playContinue);

        playButtonPanel.add(playContinue);

        playContinue.addActionListener(e ->
            gameCardLayout.show(gamePanel, "SelectionScreen")
        );

        playScreen.add(playIntro, BorderLayout.CENTER);
        playScreen.add(playButtonPanel, BorderLayout.SOUTH);

        return playScreen;
    }

    
    private JPanel createSelectionScreen() {
        JPanel selectionScreen = new JPanel(new BorderLayout());
        selectionScreen.setBackground(new Color(40, 40, 60));

        // ==== HERO GRID ====
        JPanel heroGrid = new JPanel(new GridBagLayout());
        heroGrid.setBackground(new Color(40, 40, 60));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 30, 20, 30);

        JPanel archerPanel = createHeroPanel("ARCHER", "HP: 160", "MP: 140");
        JPanel warriorPanel = createHeroPanel("WARRIOR", "HP: 200", "MP: 120");
        JPanel magePanel = createHeroPanel("MAGE", "HP: 120", "MP: 180");
        JPanel assassinPanel = createHeroPanel("ASSASSIN", "HP: 140", "MP: 150");
        JPanel tankPanel = createHeroPanel("TANK", "HP: 250", "MP: 100");

        gbc.gridx = 0; gbc.gridy = 0; heroGrid.add(archerPanel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; heroGrid.add(warriorPanel, gbc);
        gbc.gridx = 2; gbc.gridy = 0; heroGrid.add(magePanel, gbc);
        gbc.gridx = 0; gbc.gridy = 1; heroGrid.add(assassinPanel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; heroGrid.add(tankPanel, gbc);

        // Add hero grid to CENTER of BorderLayout
        selectionScreen.add(heroGrid, BorderLayout.CENTER);
   // ==== BACK BUTTON (bottom right) ====
     
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 20));
        bottomPanel.setBackground(new Color(40, 40, 60));

        JButton backButton = new JButton("BACK");
        backButton.setFont(new Font("Serif", Font.BOLD, 28));
        backButton.setForeground(Color.WHITE);
        styleButton(backButton);

        bottomPanel.add(backButton);
        selectionScreen.add(bottomPanel, BorderLayout.SOUTH);

        // ==== Click events for heroes ====
        addHeroClick(archerPanel, new Archer("Rex"));
        addHeroClick(warriorPanel, new Warrior("Adrian"));
        addHeroClick(magePanel, new Mage("Cyberg"));
        addHeroClick(assassinPanel, new Assassin("Jhush"));
        addHeroClick(tankPanel, new Tank("Clarence"));

        backButton.addActionListener(e -> gameCardLayout.show(gamePanel, "PlayScreen"));

        return selectionScreen;
    }
    
    private void addHeroClick(JPanel panel, Hero hero) {
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (count >= 3) return; // limit per side to 3 heroes

                if (isPlayerTurn) {
                    myTeam[count] = hero;
                    System.out.println("Player selected: " + hero.getName());
                } else {
                    enemyTeam[count] = hero;
                    System.out.println("Enemy selected: " + hero.getName());
                }

                count++;

                if (count == 3) {
                    if (isPlayerTurn) {
                        if(isAi){
                            // AI randomly selects 3 heroes
                            selectRandomTeamForAI();
                            System.out.println("AI team automatically selected!");
                            System.out.println("Both teams ready! Starting battle...");
                            gameCardLayout.show(gamePanel, "BattleScreen");
                        } else {
                            isPlayerTurn = false;
                            count = 0;
                            System.out.println("Now selecting enemy team...");
                        }
                    } else {
                        System.out.println("Both teams ready! Starting battle...");
                        gameCardLayout.show(gamePanel, "BattleScreen");
                    }
                }
            }
        });
    }
    private void selectRandomTeamForAI(){
         java.util.Random rand = new java.util.Random();
        Hero[] availablHeroes = {
            new Archer("Rex"),
            new Warrior("Adrian"),
            new Mage("Cyberg"),
            new Assassin("Jhush"),
            new Tank("Clarence")
        };
        for(int i = 0 ;i < 3 ; i++){
            int aiIndex = rand.nextInt(availablHeroes.length);
            enemyTeam[i] = availablHeroes[aiIndex];
            System.out.println("AI selected: " + enemyTeam[i].getName());
        }
    }

    private JPanel createHeroPanel(String name, String hp, String MP) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(180, 180));
        panel.setBackground(new Color(60, 60, 90));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));

        JLabel heroName = new JLabel(name, SwingConstants.CENTER);
        heroName.setFont(new Font("Serif", Font.BOLD, 20));
        heroName.setForeground(Color.WHITE);
        heroName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel heroHP = new JLabel(hp, SwingConstants.CENTER);
        heroHP.setForeground(Color.LIGHT_GRAY);
        heroHP.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel heroMp = new JLabel(MP, SwingConstants.CENTER);
        heroMp.setForeground(Color.LIGHT_GRAY);
        heroMp.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(20));
        panel.add(heroName);
        panel.add(Box.createVerticalStrut(10));
        panel.add(heroHP);
        panel.add(heroMp);
        panel.add(Box.createVerticalGlue());

        // Make the entire panel clickable
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return panel;
    }

    // --- Main Screen ---
    private JPanel createMainScreen() {
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(new Color(40, 40, 60));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);

        JLabel menuLogo = new JLabel("Shadows of Eldora");
        menuLogo.setFont(new Font("Serif", Font.BOLD, 48));
        menuLogo.setForeground(Color.WHITE);
        menuLogo.setHorizontalAlignment(SwingConstants.CENTER);
        menuLogo.setBorder(BorderFactory.createEmptyBorder(100, 0, 100, 0));

        JPanel menuButtons = new JPanel(new GridLayout(3, 1, 15, 15));
        menuButtons.setBackground(new Color(40, 40, 60));
        menuButtons.setBorder(BorderFactory.createEmptyBorder(0, 300, 200, 300));

        JButton playButton = new JButton("Play");
        JButton gameModeButton = new JButton("Game Mode");
        JButton exitButton = new JButton("Exit");

        JButton[] buttons = { playButton, gameModeButton, exitButton };
        for (JButton b : buttons) {
            b.setFont(new Font("Serif", Font.BOLD, 32));
            b.setForeground(Color.WHITE);
            styleButton(b);
            menuButtons.add(b);
        }

        playButton.addActionListener(e ->
            gameCardLayout.show(gamePanel, "PlayScreen")
        );
        gameModeButton.addActionListener(e ->
            gameCardLayout.show(gamePanel, "GameMode")
        );
        exitButton.addActionListener(e -> System.exit(0));

        centerPanel.add(menuLogo, BorderLayout.NORTH);
        centerPanel.add(menuButtons, BorderLayout.CENTER);
        menuPanel.add(centerPanel, BorderLayout.CENTER);

        return menuPanel;
    }

    // --- Game Mode Screen ---
    private JPanel createGameModeScreen() {
        JPanel settingPanel = new JPanel(new BorderLayout());
        settingPanel.setBackground(new Color(40, 40, 60));

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(new Color(40, 40, 60));

        JButton pvpButton = new JButton("PLAYER VS PLAYER");
        JButton pvaiButton = new JButton("PLAYER VS AI");

        JButton[] buttons = { pvpButton, pvaiButton };
        for (JButton b : buttons) {
            b.setFont(new Font("Serif", Font.BOLD, 32));
            b.setForeground(Color.WHITE);
            styleButton(b);
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            center.add(Box.createRigidArea(new Dimension(0, 30)));
            center.add(b);
        }

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(new Color(40, 40, 60));
        wrapper.add(center);

        settingPanel.add(wrapper, BorderLayout.CENTER);

        pvpButton.addActionListener(e -> {
            isAi = false;
            gameCardLayout.show(gamePanel, "MainScreen");
        });

        pvaiButton.addActionListener(e -> {
            isAi = true;
            gameCardLayout.show(gamePanel, "MainScreen");
        });

        return settingPanel;
    }

    
    private void styleButton(JButton b) {
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setOpaque(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameGUI::new);
    }
}