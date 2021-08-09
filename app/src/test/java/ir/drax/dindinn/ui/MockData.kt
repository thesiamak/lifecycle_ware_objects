package ir.drax.dindinn.ui

import com.interview.blocket.data.model.Repository
import com.interview.blocket.data.model.User

object MockData {

    val mockUser = User(id = "id", avatarUrl = "avatarUrl", name = "user", followers = 1, following = 1, publicRepos = 1)

    val mockRepository = Repository(
        contributorsUrl = "url",
        createdAt = "1",
        description = "description",
        language = "lang",
        name = "name",
        fullName = "fullName",
        isPrivate = true,
        updatedAt = "1",
        owner = mockUser
    )
}
