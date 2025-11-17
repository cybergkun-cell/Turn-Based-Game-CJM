import javax.swing.*;
import java.awt.*;

class ImageBackgroundPanel extends JPanel {
    private Image backgroundImage;

    public ImageBackgroundPanel(String imagePath) {
        try {
            backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

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

        add(gamePanel);
        gameCardLayout.show(gamePanel, "MainScreen");

        setVisible(true);
    }

    
    private JPanel createPlayScreen() {
        ImageBackgroundPanel playScreen = new ImageBackgroundPanel("/assets/eldora-background.png");
        playScreen.setLayout(new BorderLayout());

        JPanel playIntro = new JPanel(new BorderLayout());
        playIntro.setOpaque(false); // Transparent background

        JTextArea playStory = new JTextArea(
            "Long ago, the peaceful village of Eldora was destroyed by the Overlord of Shadows, " +
            "leaving five childhood friends as its only survivors. Adrian the Warrior, Jhush the " +
            "Assassin, Cyberg the Mage, Rex the Archer, and Clarence the Tank swore a blood oath " +
            "to fight as brothers and avenge their fallen home.\n\n" +
            "Each trained in their own discipline: strength, speed, magic, precision, and defense, " +
            "becoming the last sons of Eldonia. United by brotherhood and destiny, they now battle " +
            "together to restore light to the kingdom."
        );
        playStory.setForeground(Color.WHITE);
        playStory.setFont(new Font("Serif", Font.PLAIN, 18));
        playStory.setOpaque(false); // Transparent background
        playStory.setEditable(false);
        playStory.setFocusable(false);
        playStory.setLineWrap(true);
        playStory.setWrapStyleWord(true);
        playStory.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JScrollPane scrollPane = new JScrollPane(playStory);
        scrollPane.setOpaque(false); // Transparent background
        scrollPane.setBorder(null);
        scrollPane.getViewport().setOpaque(false); // Transparent viewport
        playIntro.add(scrollPane, BorderLayout.CENTER);

        JPanel playButtonPanel = new JPanel();
        playButtonPanel.setOpaque(false); // Transparent background

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
    ImageBackgroundPanel selectionScreen = new ImageBackgroundPanel("/assets/eldora-background.png");
    selectionScreen.setLayout(new BorderLayout());

    // ==== HERO GRID ====
    JPanel heroGrid = new JPanel(new GridBagLayout());
    heroGrid.setOpaque(false);
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

    selectionScreen.add(heroGrid, BorderLayout.CENTER);
    
    // ==== BOTTOM PANEL with BACK (left) and CONTINUE (right) ====
    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.setOpaque(false);
    bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

    // BACK button on the left
    JButton backButton = new JButton("BACK");
    backButton.setFont(new Font("Serif", Font.BOLD, 28));
    backButton.setForeground(Color.WHITE);
    styleButton(backButton);

    // CONTINUE button on the right
    JButton continueButton = new JButton("CONTINUE");
    continueButton.setFont(new Font("Serif", Font.BOLD, 28));
    continueButton.setForeground(Color.WHITE);
    styleButton(continueButton);

    // Add buttons to panel
    bottomPanel.add(backButton, BorderLayout.WEST);
    bottomPanel.add(continueButton, BorderLayout.EAST);

    selectionScreen.add(bottomPanel, BorderLayout.SOUTH);

    // ==== Click events for heroes ====
    addHeroClick(archerPanel, new Archer("Rex"));
    addHeroClick(warriorPanel, new Warrior("Adrian"));
    addHeroClick(magePanel, new Mage("Cyberg"));
    addHeroClick(assassinPanel, new Assassin("Jhush"));
    addHeroClick(tankPanel, new Tank("Clarence"));

    // ==== Button actions ====
    backButton.addActionListener(e -> gameCardLayout.show(gamePanel, "PlayScreen"));
    
    // CONTINUE button action (placeholder - does nothing for now)
    continueButton.addActionListener(e -> {
        // This will be implemented later
        System.out.println("Continue button clicked - functionality to be added");
    });

    return selectionScreen;
}

    // --- Game Mode Screen ---
    private JPanel createGameModeScreen() {
        ImageBackgroundPanel settingPanel = new ImageBackgroundPanel("/assets/eldora-background.png");
        settingPanel.setLayout(new BorderLayout());

        // Center panel (vertical layout)
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false); // Transparent background

        // Buttons
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

        // Wrapper with GridBag for perfect centering
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false); // Transparent background
        wrapper.add(center);

        settingPanel.add(wrapper, BorderLayout.CENTER);

        // Action listeners
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

    // --- Main Screen --- (keeps the castle background)
    private JPanel createMainScreen() {
        ImageBackgroundPanel menuPanel = new ImageBackgroundPanel("/assets/castle.png");
        menuPanel.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);

        JLabel menuLogo = new JLabel("Shadows of Eldora");
        menuLogo.setFont(new Font("Serif", Font.BOLD, 84));
        menuLogo.setForeground(Color.WHITE);
        menuLogo.setHorizontalAlignment(SwingConstants.CENTER);
        menuLogo.setBorder(BorderFactory.createEmptyBorder(100, 0, 100, 0));

        JPanel menuButtons = new JPanel(new GridLayout(3, 1, 15, 15));
        menuButtons.setOpaque(false);
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

        playButton.addActionListener(e -> gameCardLayout.show(gamePanel, "PlayScreen"));
        gameModeButton.addActionListener(e -> gameCardLayout.show(gamePanel, "GameMode"));
        exitButton.addActionListener(e -> System.exit(0));

        centerPanel.add(menuLogo, BorderLayout.NORTH);
        centerPanel.add(menuButtons, BorderLayout.CENTER);
        menuPanel.add(centerPanel, BorderLayout.CENTER);

        return menuPanel;
    }
    
    private void addHeroClick(JPanel panel, Hero hero) {
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (count >= 3) return;

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
        panel.setBackground(new Color(60, 60, 90, 180)); // Semi-transparent for readability
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