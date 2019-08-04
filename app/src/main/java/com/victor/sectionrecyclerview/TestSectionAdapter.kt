package com.victor.sectionrecyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.victor.sectionrecyclerview2.SectionRecyclerViewAdapter

/**
 * Created on 2019-08-03.
 * vvictorwan@gmail.com
 */
class TestSectionAdapter: SectionRecyclerViewAdapter() {

    override fun numberOfSections(): Int {
        return 2
    }

    override fun numberOfRowsInSection(section: Int): Int {
        return 8
    }

    override fun hasHeaderInSection(section: Int): Boolean {
        if (section == 0) {
            return true
        }
        return false
    }

    override fun onCreateHeaderViewHolderInSection(parent: ViewGroup, section: Int): RecyclerView.ViewHolder? {
        if (section == 0) {
            return TestHolderOneHeader(parent)
        }
        return super.onCreateHeaderViewHolderInSection(parent, section)
    }

    override fun hasFooterInSection(section: Int): Boolean {
        if (section == 1) {
            return true
        }
        return false
    }

    override fun onCreateFooterViewHolderInSection(parent: ViewGroup, section: Int): RecyclerView.ViewHolder? {
        if (section == 1) {
            return TestHolderTwoFooter(parent)
        }
        return super.onCreateFooterViewHolderInSection(parent, section)
    }

    override fun onCreateViewHolderInSection(
        parent: ViewGroup,
        section: Int
    ): android.support.v7.widget.RecyclerView.ViewHolder {
        return when (section) {
            0 -> TestHolderOne(parent)
            1 -> TestHolderTwo(parent)
            else -> TestHolderTwo(parent)
        }
    }

    override fun onBindContentViewHolderInSection(
        position: Int,
        section: Int,
        holder: android.support.v7.widget.RecyclerView.ViewHolder
    ) {

    }

    class TestHolderOne(parent: ViewGroup): RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.test_viewholder_one, parent, false)) {
    }

    class TestHolderOneHeader(parent: ViewGroup): RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.test_viewholder_one_header, parent, false)) {
    }

    class TestHolderTwo(parent: ViewGroup): RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.test_viewholder_two, parent, false)) {
    }

    class TestHolderTwoFooter(parent: ViewGroup): RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.test_viewholder_two_footer, parent, false)) {
    }
}