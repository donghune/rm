import entity.OverrideEntity
import entity.Predicate
import entity.ResourceModel
import entity.Textures
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileWriter
import java.nio.file.CopyOption
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import javax.imageio.ImageIO
import javax.imageio.ImageWriter


const val modelFolder = "/Users/yudonghun/IdeaProjects/resource/resources/mindew/assets/minecraft/models"
const val texturesFolder = "/Users/yudonghun/IdeaProjects/resource/resources/mindew/assets/minecraft/textures"

fun main() {
    dew()
}

fun dew() {
    val folders = File("/Users/yudonghun/IdeaProjects/resource/base").listFiles() ?: return
    var groupId = 0
    val overrides = mutableListOf<OverrideEntity>()
    folders.sortedBy { it.name }.forEach { folder ->
        println(folder)
        var index = 1
        groupId += 1000
        (folder.listFiles() ?: return).forEach { file ->
            val from = File(folder.path, file.name)
            File(texturesFolder + "/" + folder.name.replace(Regex("[0-9]"), "")).mkdirs()
            val to = File(texturesFolder + "/" + folder.name.replace(Regex("[0-9]"), ""), file.name.toLowerCase())
            ImageIO.write(ImageIO.read(from), "png", to)

            println(
                """
                DewItem(
                    code = ${groupId + index},
                    name = "${file.nameWithoutExtension.toLowerCase()}",
                    displayName = "${file.nameWithoutExtension.toLowerCase()}",
                    lore = listOf(""),
                    grade = DewGrade.NORMAL
                )
            """.trimIndent()
            )

            overrides.add(
                OverrideEntity(
                    model = "${folder.name.replace(Regex("[0-9]"), "")}/${file.nameWithoutExtension.toLowerCase()}",
                    predicate = Predicate(customModelData = groupId + index)
                )
            )
            index++

            val f =
                File(
                    "/Users/yudonghun/IdeaProjects/resource/resources/mindew/assets/minecraft/models/${
                        folder.name.replace(
                            Regex("[0-9]"), ""
                        ).toLowerCase()
                    }"
                )
            f.mkdir()
            val fw = FileWriter(File(f, "${file.nameWithoutExtension.toLowerCase()}.json"))
            fw.write(
                """
                {
                    "parent": "minecraft:item/handheld",
                    "textures": {
                        "layer0": "${
                    folder.name.replace(
                        Regex(
                            "[0-9]"
                        ),
                        ""
                    )
                }/${file.nameWithoutExtension.toLowerCase()}"
                    }
                }
            """.trimIndent()
            )
            fw.flush()
        }
        index = 1
    }
    val resourceModel = ResourceModel(
        parent = "item/handheld",
        textures = Textures(
            layer0 = "item/emerald"
        ),
        overrides = overrides,
    )

    val folder = File("$modelFolder/item/")
    if (!folder.exists()) {
        folder.mkdir()
    }
    val jsonFile = File(folder, "emerald.json")

    val writer = FileWriter(jsonFile)
    writer.write(
        Json { prettyPrint = true }.encodeToString(resourceModel)
    )
    writer.flush()
}

// kim
fun drawTime(group: String, text: String, drawText: String = text) {
    val bufferedImage = ImageIO.read(File("/Users/yudonghun/IdeaProjects/untitled/src/main/resources/menu.png"))

    val graphics = bufferedImage.graphics
    graphics.color = Color.BLACK
    graphics.font = Font("Minecraft", Font.PLAIN, 30)
    graphics.drawString(drawText, 450, 560)
    val folder = File("$texturesFolder/$group/")
    if (!folder.exists()) {
        folder.mkdir()
    }
    ImageIO.write(bufferedImage, "png", File(folder, "${text}.png"))
}

fun createImageFile(group: String, text: String, drawText: String = text) {
    val bufferedImage = BufferedImage(
        64, 64,
        BufferedImage.TYPE_INT_ARGB
    )

    val graphics = bufferedImage.graphics
    graphics.color = Color.BLACK
    graphics.font = Font("Minecraft", Font.PLAIN, 50)
    graphics.drawString(drawText, 5, 50)
    val folder = File("$texturesFolder/$group/")
    if (!folder.exists()) {
        folder.mkdir()
    }
    ImageIO.write(bufferedImage, "png", File(folder, "${text}.png"))
}

fun printCMD(cmd: Int, group: String, text: String) {
    println(
        """
		{
			"predicate": {
				"custom_model_data": $cmd
			},
			"model": "$group/$text"
		},
        """.trimIndent()
    )
}


fun numberMaker() {
    // number maker
    val overrides = mutableListOf<OverrideEntity>()
    val group = "menu"

    for (i in 0..(60 * 30)) {
        val name = "menu_$i"
        drawTime(group, name, "%02d:%02d:%02d".format(i / 1800, i % 1800 / 60, i % 1800 % 60))
        overrides.add(
            OverrideEntity(
                model = "$group/$name",
                predicate = Predicate(customModelData = i + 1)
            )
        )
        val writer = FileWriter(File("$modelFolder/menu", "$name.json"))
        writer.write(
            """
            {
                "credit": "Made with Blockbench",
                "textures": {
                    "0": "menu/$name"
                },
                "elements": [
                    {
                        "from": [-16, 0, -16],
                        "to": [28, 0, 19],
                        "faces": {
                            "north": {"uv": [0, 0, 16, 1], "texture": "#0"},
                            "east": {"uv": [0, 0, 12, 1], "texture": "#0"},
                            "south": {"uv": [0, 0, 16, 1], "texture": "#0"},
                            "west": {"uv": [0, 0, 12, 1], "texture": "#0"},
                            "up": {"uv": [0, 0, 16, 16], "texture": "#0"},
                            "down": {"uv": [0, 0, 16, 12], "texture": "#0"}
                        }
                    }
                ],
                "gui_light": "front",
                "display": {
                    "gui": {
                        "rotation": [90, 0, 0],
                        "translation": [-63, 35, 1],
                        "scale": [4, 1, 4]
                    }
                }
            }
        """.trimIndent()
        )
        writer.flush()
    }

    val resourceModel = ResourceModel(
        parent = "item/handheld",
        textures = Textures(
            layer0 = "item/emerald"
        ),
        overrides = overrides,
    )

    val folder = File("$modelFolder/item/")
    if (!folder.exists()) {
        folder.mkdir()
    }
    val jsonFile = File(folder, "emerald.json")

    val writer = FileWriter(jsonFile)
    writer.write(
        Json { prettyPrint = true }.encodeToString(resourceModel)
    )
    writer.flush()
}