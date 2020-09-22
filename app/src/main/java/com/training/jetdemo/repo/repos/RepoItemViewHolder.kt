package com.training.jetdemo.repo.repos

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.training.jetdemo.R
import com.training.jetdemo.base.BaseItemViewHolder
import com.training.jetdemo.data.local.db.entity.RepoEntity
import com.training.jetdemo.di.component.ViewHolderComponent
import kotlinx.android.synthetic.main.item_view_repo.view.*

class RepoItemViewHolder(parent: ViewGroup) :
    BaseItemViewHolder<RepoEntity, RepoItemViewModel>(R.layout.item_view_repo, parent) {

    override fun injectDependencies(viewHolderComponent: ViewHolderComponent) {
        viewHolderComponent.inject(this)
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.name.observe(this, Observer {
            itemView.tvName.text = "Name : $it"
        })

        viewModel.userDescription.observe(this, Observer {
            itemView.tvDescription.text = "Description: $it"
        })

        viewModel.issueCount.observe(this, Observer {
            itemView.tvIssueCount.text = "$it Issue Count"
        })


        viewModel.permissions.observe(this, Observer {
            itemView.tvPermission.text = "${it}"
        })

        viewModel.license.observe(this, Observer {
            itemView.tvLicense.text = "${it}"
        })

        viewModel.url.observe(this, Observer {
            itemView.tvUrl.text = "URL : $it "
        })


    }

    override fun setupView(view: View) {
    }
}