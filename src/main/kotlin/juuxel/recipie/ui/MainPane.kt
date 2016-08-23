package juuxel.recipie.ui

import juuxel.recipie.l10n.*
import juuxel.recipie.recipe.*
import java.awt.*
import javax.swing.*
import javax.swing.event.*

class MainPane : JTabbedPane(JTabbedPane.BOTTOM)
{
    private var settingsDialog = lazy { SettingsDialog() }

    private val buttonHTML = "<html><b>"
    private val buttonHTMLEnd = "</b>"

    init
    {
        invokeOnEDT { constructUI() }
    }

    // Constructs the UI
    private fun constructUI()
    {
        val mainView = JPanel()
        val mainViewTitle = JPanel()
        val actionPane = JPanel(GridLayout(0, 1))
        val settingsButton = JButton(Images.newIcon(Images.SETTINGS_ICON))
        val list = JList<Recipe>()
        val listScrollPane = JScrollPane(list)

        val newRecipe = JButton(
                buttonHTML + L10n["recipe.action.create"] + buttonHTMLEnd,
                Images.newIcon(Images.CREATE_RECIPE, 48, 48))

        val importRecipe = JButton(
                buttonHTML + L10n["recipe.action.import"] + buttonHTMLEnd,
                Images.newIcon(Images.IMPORT_RECIPE, 48, 48))

        val searchButton = JButton(
                buttonHTML + L10n["recipe.action.search"] + buttonHTMLEnd,
                Images.newIcon(Images.SEARCH, 48, 48))

        listScrollPane.border = BorderFactory.createTitledBorder(L10n["recipe.listTitle"])

        actionPane.maximumSize = Dimension(64, Integer.MAX_VALUE)
        actionPane.add(newRecipe)
        actionPane.add(importRecipe)
        actionPane.add(searchButton)

        mainView.layout = BoxLayout(mainView, BoxLayout.X_AXIS)
        mainView.add(listScrollPane)
        mainView.add(actionPane)

        settingsButton.toolTipText = L10n["settings.title"]
        settingsButton.addActionListener({ e ->
            settingsDialog.value.isVisible = true
        })

        list.addListSelectionListener { this.onItemSelected(it) }

        mainViewTitle.layout = BoxLayout(mainViewTitle, BoxLayout.X_AXIS)
        mainViewTitle.isOpaque = false
        mainViewTitle.add(JLabel(L10n["tabs.main"]))
        mainViewTitle.add(Box.createHorizontalStrut(4))
        mainViewTitle.add(settingsButton)

        addTab(L10n["tabs.error"], mainView)
        setTabComponentAt(0, mainViewTitle)
    }

    // Called when a recipe is selected from
    // the recipe list
    private fun onItemSelected(e: ListSelectionEvent)
    {
        val list = e.source as JList<*>
        list.selectedIndex = -1
    }
}

