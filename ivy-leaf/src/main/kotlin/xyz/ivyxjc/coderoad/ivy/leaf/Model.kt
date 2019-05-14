package xyz.ivyxjc.coderoad.ivy.leaf


data class Result(val id: Long, val status: Status)


enum class Status {
    SUCCESS,
    EXCEPTION
}
