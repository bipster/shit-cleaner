package net.ntworld.intellijCodeCleaner.data

import net.ntworld.intellijCodeCleaner.ISSUE_NODE_TYPE_DIRECTORY
import net.ntworld.intellijCodeCleaner.ISSUE_NODE_TYPE_FILE
import net.ntworld.intellijCodeCleaner.ISSUE_NODE_TYPE_ROOT
import net.ntworld.intellijCodeCleaner.data.internal.IssueNodeDataImpl
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

class IssueNodeDataBuilderTest {
    @Test
    fun `test rootNote should be created automatically`() {
        val builder = IssueNodeDataBuilder()
        assertEquals(ISSUE_NODE_TYPE_ROOT, builder.rootNodeData.type)
        assertEquals("", builder.rootNodeData.name)
    }

    @Test
    fun `test rootNote can be passed outside`() {
        val root = IssueNodeDataImpl(
            type = ISSUE_NODE_TYPE_ROOT,
            name = "~/test"
        )
        val builder = IssueNodeDataBuilder(root)
        assertSame(root, builder.rootNodeData)
    }

    @Test
    fun `test appendPathComponentsToTree can create leap in Tree`() {
        val components = listOf("first", "second", "third", "File.kt")
        val builder = IssueNodeDataBuilder()
        builder.appendPathComponentsToTree(components, "", "")

        expectDirectoryNode(findByPath(builder, 0), "first")
        expectDirectoryNode(findByPath(builder, 0, 0), "second")
        expectDirectoryNode(findByPath(builder, 0, 0, 0), "third")
        expectFileNode(findByPath(builder, 0, 0, 0, 0), "File.kt")
    }

    @Test
    fun `test appendPathComponentsToTree can update leap in Tree`() {
        val componentsOne = listOf("first", "second", "third", "File.kt")
        val componentsTwo = listOf("first", "new", "Update.kt")
        val builder = IssueNodeDataBuilder()
        builder.appendPathComponentsToTree(componentsOne, "", "")
        builder.appendPathComponentsToTree(componentsTwo, "", "")

        expectDirectoryNode(findByPath(builder, 0), "first")

        expectDirectoryNode(findByPath(builder, 0, 0), "second")
        expectDirectoryNode(findByPath(builder, 0, 1), "new")

        expectDirectoryNode(findByPath(builder, 0, 0, 0), "third")
        expectFileNode(findByPath(builder, 0, 1, 0), "Update.kt")

        expectFileNode(findByPath(builder, 0, 0, 0, 0), "File.kt")
    }

    @Test
    fun `test appendPathComponentsToTree can create directory and file as the same name`() {
        val componentsOne = listOf("first", "second", "File.kt")
        val componentsTwo = listOf("first", "second")
        val builder = IssueNodeDataBuilder()
        builder.appendPathComponentsToTree(componentsOne, "", "")
        builder.appendPathComponentsToTree(componentsTwo, "", "")

        expectDirectoryNode(findByPath(builder, 0), "first")

        expectDirectoryNode(findByPath(builder, 0, 0), "second")
        expectFileNode(findByPath(builder, 0, 1), "second")

        expectFileNode(findByPath(builder, 0, 0, 0), "File.kt")
    }

    private fun expectDirectoryNode(nodeData: IssueNodeData, name: String) {
        assertEquals(ISSUE_NODE_TYPE_DIRECTORY, nodeData.type)
        assertEquals(name, nodeData.name)
    }

    private fun expectFileNode(nodeData: IssueNodeData, name: String) {
        assertEquals(ISSUE_NODE_TYPE_FILE, nodeData.type)
        assertEquals(name, nodeData.name)
    }

    private fun findByPath(builder: IssueNodeDataBuilder, vararg index: Int): IssueNodeData {
        var node = builder.rootNodeData
        val indexes = index.toList()
        for (i in 0 until indexes.size) {
            node = node.children[indexes[i]]
        }
        return node
    }
}