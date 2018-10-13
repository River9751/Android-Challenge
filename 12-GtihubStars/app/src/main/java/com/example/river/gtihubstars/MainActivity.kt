package com.example.river.gtihubstars

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search.setOnClickListener {
            val username = editText.text
            val url = "https://api.github.com/users/$username/starred"

            Thread {
                var response = OkHttpHelper.getResponse(url)

                var projectList = getProjectList(response)

                var intent = Intent(this, InfoActivity::class.java)

//                var bundle = Bundle()
//                bundle.putParcelableArrayList("projects", ArrayList(projectList))
                intent.putParcelableArrayListExtra("projects", ArrayList(projectList))

                startActivity(intent)
            }.start()
        }
    }

    fun getProjectList(json: JSONArray): MutableList<ProjectModel> {

        val projectList = mutableListOf<ProjectModel>()
        for (i in 0 until json.length()) {
            val item = json.getJSONObject(i)

            val owner = item.getJSONObject("owner")
            val ownerName = owner.get("login").toString()
            val avatarURL = owner.get("avatar_url").toString()
            val projectName = item.get("name").toString()
            val description = item.get("description").toString()
            val starCount = item.get("stargazers_count").toString().toInt()
            val forkCount = item.get("forks_count").toString().toInt()

            val project =
                ProjectModel(projectName, description, avatarURL, starCount, forkCount, ownerName)
            projectList.add(project)
        }

        return projectList
    }
}