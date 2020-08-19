package com.gas.zhihu.fragment.paper

interface PreferTitle {
    val title: String
}

enum class PaperType(val typeId: Int) : PreferTitle {
    NULL(-1) {
        override val title: String
            get() = ""
    },
    PAPER(0) {
        override val title: String
            get() = "图纸"

    },
    EXPERIENCE(1) {
        override val title: String
            get() = "消缺经验集"

    },
    GRAPH(2) {
        override val title: String
            get() = "样本"

    };

    companion object {
        fun parseFromInt(typeId: Int) = values().find { it.typeId == typeId } ?: NULL
    }
}

enum class FileType(val typeId: Int) : PreferTitle {
    NULL(-1) {
        override val title: String
            get() = ""
    },
    PAPER(0) {
        override val title: String
            get() = "图纸"

    },
    EXPERIENCE(1) {
        override val title: String
            get() = "消缺经验集"

    },
    GRAPH(2) {
        override val title: String
            get() = "样本"

    };

    companion object {
        fun parseFromInt(typeId: Int) = values().find { it.typeId == typeId } ?: NULL
    }
}