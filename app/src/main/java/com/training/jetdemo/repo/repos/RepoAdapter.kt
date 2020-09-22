package com.training.jetdemo.repo.repos

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import com.training.jetdemo.data.local.db.entity.RepoEntity

class RepoAdapter(
    parentLifecycle: Lifecycle,
    posts: ArrayList<RepoEntity>
) : com.training.jetdemo.base.BaseAdapter<RepoEntity, RepoItemViewHolder>(parentLifecycle, posts) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RepoItemViewHolder(parent)
}