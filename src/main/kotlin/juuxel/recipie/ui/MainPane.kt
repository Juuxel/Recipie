package juuxel.recipie.ui

import com.alee.laf.filechooser.*
import com.alee.laf.tabbedpane.*
import com.alee.laf.text.*
import com.beust.klaxon.*
import com.beust.klaxon.Parser
import juuxel.recipie.*
import juuxel.recipie.l10n.*
import juuxel.recipie.recipe.*
import org.pegdown.*
import java.awt.*
import javax.swing.*
import javax.swing.event.*

class MainPane : WebTabbedPane(WebTabbedPane.BOTTOM)
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

        importRecipe.addActionListener {
            val fileChooser = WebFileChooser()
            fileChooser.fileFilter = recipeFilter

            do
            {
                val num = fileChooser.showOpenDialog(frame.value)
            } while (num != WebFileChooser.APPROVE_OPTION)

            val file = fileChooser.selectedFile
            val recipe = recipeOf(Parser().parse(file.inputStream()) as JsonObject)

            val processor = PegDownProcessor()
            val html = processor.markdownToHtml(recipe.instructions.joinToString(separator = "\n"))
            val comp = WebTextPane(html.htmlDocument())

            addTab(recipe.nameWithAuthor, comp)
            setTabComponentAt(indexOfComponent(comp), createTabTitleComponent(
                    recipe.nameWithAuthor,
                    createTabCloseButton(this, comp)
            ))
            selectedComponent = comp
        }

        settingsButton.toolTipText = L10n["settings.title"]
        settingsButton.addActionListener({ e ->
            settingsDialog.value.isVisible = true
        })

        list.addListSelectionListener { this.onItemSelected(it) }

        addTab(L10n["tabs.error"], mainView)
        setTabComponentAt(0, createTabTitleComponent(localize("tabs.main"), settingsButton))
    }

    // Called when a recipe is selected from
    // the recipe list
    private fun onItemSelected(e: ListSelectionEvent)
    {
        val list = e.source as JList<*>
        list.selectedIndex = -1
    }
}

