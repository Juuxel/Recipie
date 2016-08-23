package juuxel.recipie.ui

import com.beust.klaxon.*
import juuxel.recipie.*
import juuxel.recipie.l10n.*
import juuxel.recipie.ui.skin.*
import java.awt.*
import javax.swing.*

class SettingsDialog : JDialog(frame.value)
{
    init
    {
        contentPane = constructUI()
        setIconImage(Images.SETTINGS_ICON)
        title = L10n["settings.title"]
        setSize(440, 340)
        defaultCloseOperation = WindowConstants.HIDE_ON_CLOSE
    }

    private fun constructUI(): JPanel
    {
        // Components
        val pane = JPanel(BorderLayout())
        val tabbedPane = JTabbedPane()
        val closeButton = JButton(L10n["settings.close"])
        val saveButton = JButton(L10n["settings.save"])
        val saveWarningLabel = JLabel(L10n["settings.saveWarning"])
        val controlPane = JPanel()
        val about = JPanel()
        val title = JLabel("<html><h1>${L10n["settings.about.versionFormat", "version".to(version)]}</h1>")
        val iconInfo = JLabel(L10n["settings.about.iconInfo", "url".to("https://www.icons8.com/")])
        val appearancePane = JPanel()
        val skinPane = JPanel(GridLayout(1, 0))
        val skinLabel = JLabel(L10n["settings.appearance.skins"])
        val skinBox = JComboBox<Skin>(skins.toTypedArray())
        val skinWarningLabel = JLabel("<html><b>${L10n["settings.appearance.skins.warning"]}</b>")

        // Listeners
        val settingsListener: ((Any) -> Unit) = { saveButton.isEnabled = true }
        fun settingsUpdater(key: String): ((Any) -> Unit) { return {
            settings.update(key)
            settingsListener.invoke(it)
        } }

        // Common GUI
        saveButton.addActionListener {
            { settings.exportFile() }.invokeSafely()
            saveButton.isEnabled = false
        }

        closeButton.addActionListener { e -> isVisible = false }

        controlPane.layout = BoxLayout(controlPane, BoxLayout.X_AXIS)
        controlPane.add(Box.createHorizontalGlue())
        controlPane.add(saveWarningLabel)
        controlPane.add(Box.createHorizontalStrut(5))
        controlPane.add(saveButton)
        controlPane.add(closeButton)

        // About GUI
        about.layout = BoxLayout(about, BoxLayout.Y_AXIS)
        about.add(title)
        about.add(iconInfo)

        // Appearance GUI
        appearancePane.layout = BoxLayout(appearancePane, BoxLayout.Y_AXIS)

        settings.map("skin", { skinBox.selectedItem })
        skinBox.addActionListener(settingsUpdater("skin"))

        val selectedSkin = skins.find { it.name == settings.json.string("skin") }

        if (selectedSkin != null)
            skinBox.selectedItem = selectedSkin

        skinPane.add(skinLabel)
        skinPane.add(skinBox)
        skinPane.maximumSize = Dimension(Int.MAX_VALUE, 32)

        appearancePane.add(skinPane)
        appearancePane.add(skinWarningLabel)
        appearancePane.add(Box.createVerticalGlue())

        // Tabs
        tabbedPane.addTab(L10n["settings.appearance.title"], appearancePane)
        tabbedPane.addTab(L10n["settings.about.title"], about)

        // This is done here because setting the defaults
        // triggers the ActionListener
        saveButton.isEnabled = false

        pane.add(tabbedPane, BorderLayout.CENTER)
        pane.add(controlPane, BorderLayout.SOUTH)

        /* TODO SETTINGS */
        return pane
    }
}
