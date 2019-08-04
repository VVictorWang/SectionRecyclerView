package com.victor.sectionrecyclerview2

import android.support.annotation.IntRange
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created on 2019-08-03.
 * vvictorwan@gmail.com
 */

abstract class SectionRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    @IntRange(from = 0)
    abstract fun numberOfSections(): Int

    @IntRange(from = 0)
    abstract fun numberOfRowsInSection(section: Int): Int

    abstract fun onCreateViewHolderInSection(parent: ViewGroup, section: Int): RecyclerView.ViewHolder
    abstract fun onBindContentViewHolderInSection(position: Int, section: Int, holder: RecyclerView.ViewHolder)

    open fun hasHeaderInSection(section: Int): Boolean = false
    open fun onCreateHeaderViewHolderInSection(parent: ViewGroup, section: Int): RecyclerView.ViewHolder? = null
    open fun onBindHeaderViewHolderInSection(section: Int, holder: RecyclerView.ViewHolder) {}

    open fun hasFooterInSection(section: Int): Boolean = false
    open fun onCreateFooterViewHolderInSection(parent: ViewGroup, section: Int): RecyclerView.ViewHolder? = null
    open fun onBindFooterViewHolderInSection(section: Int, holder: RecyclerView.ViewHolder) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val section = getSectionByItemViewType(viewType)
        val sectionType = getSectionTypeByItemViewType(viewType)
        if (sectionType == SectionType.Header && hasHeaderInSection(section)) {
            return onCreateHeaderViewHolderInSection(parent, section)!!
        } else if (sectionType == SectionType.Footer && hasFooterInSection(section)) {
            return onCreateFooterViewHolderInSection(parent, section)!!
        }
        return onCreateViewHolderInSection(parent, section)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val sectionAndPos = getItemSectionWithTypeAndPositionInSection(position)
        when (sectionAndPos.second) {
            SectionType.Header -> onBindHeaderViewHolderInSection(section = sectionAndPos.first, holder = holder)
            SectionType.Footer -> onBindFooterViewHolderInSection(section = sectionAndPos.first, holder = holder)
            SectionType.Content -> onBindContentViewHolderInSection(
                position = sectionAndPos.third,
                section = sectionAndPos.first,
                holder = holder
            )
        }
    }

    override fun getItemCount(): Int {
        var totalCount = 0
        for (i in 0 until numberOfSections()) {
            totalCount += numberOfRowsInSection(i)
            totalCount += if (hasHeaderInSection(i)) 1 else 0
            totalCount += if (hasFooterInSection(i)) 1 else 0
        }
        return totalCount
    }

    override fun getItemViewType(position: Int): Int {
        val section = getItemSectionWithType(position)
        return getItemViewTypeForSection(section.first, section.second)
    }

    private fun getItemSectionWithType(position: Int): Pair<Int, SectionType> {
        var currentPos = position
        var section = 0
        var type: SectionType = SectionType.Content
        var totalCountInSection = numberOfRowsInSection(section)
        if (hasHeaderInSection(section)) {
            totalCountInSection++
        }
        if (hasFooterInSection(section)) {
            totalCountInSection++
        }
        if (currentPos == 0 && hasHeaderInSection(section)) {
            type = SectionType.Header
        }
        if (currentPos == totalCountInSection - 1 && hasFooterInSection(section)) {
            type = SectionType.Footer
        }
        while (currentPos >= totalCountInSection) {
            currentPos -= totalCountInSection
            section++
            totalCountInSection = numberOfRowsInSection(section)
            if (hasHeaderInSection(section)) {
                totalCountInSection++
            }
            if (hasFooterInSection(section)) {
                totalCountInSection++
            }
            type = if (currentPos == 0 && hasHeaderInSection(section)) SectionType.Header
            else if (currentPos == totalCountInSection - 1 && hasFooterInSection(section)) SectionType.Footer else SectionType.Content
        }
        return Pair(first = section, second = type)
    }

    private fun getItemSectionWithTypeAndPositionInSection(position: Int): Triple<Int, SectionType, Int> {
        var currentPos = position
        var section = 0
        var type: SectionType = SectionType.Content
        var totalCountInSection = numberOfRowsInSection(section)
        if (hasHeaderInSection(section)) {
            totalCountInSection++
        }
        if (hasFooterInSection(section)) {
            totalCountInSection++
        }
        if (currentPos == 0 && hasHeaderInSection(section)) {
            type = SectionType.Header
        }
        while (currentPos >= totalCountInSection) {
            currentPos -= totalCountInSection
            section++
            totalCountInSection = numberOfRowsInSection(section)
            if (hasHeaderInSection(section)) {
                totalCountInSection++
            }
            if (hasFooterInSection(section)) {
                totalCountInSection++
            }
            type = if (currentPos == 0 && hasHeaderInSection(section)) SectionType.Header
            else if (currentPos == totalCountInSection - 1 && hasFooterInSection(section)) SectionType.Footer else SectionType.Content
        }
        return Triple(first = section, second = type, third = currentPos)
    }

    private fun getItemViewTypeForSection(section: Int, sectionType: SectionType): Int {
        return when (sectionType) {
            SectionType.Content -> section + 10
            SectionType.Header -> -(section + 15 + numberOfSections())
            SectionType.Footer -> -(section + 10)
        }
    }

    private fun getSectionByItemViewType(viewType: Int): Int {
        return when (viewType) {
            in Int.MIN_VALUE..(-numberOfSections() - 15) -> -(viewType + 15 + numberOfSections())
            in (-numberOfSections() - 15)..0 -> -(viewType + 10)
            else -> viewType - 10
        }
    }

    private fun getSectionTypeByItemViewType(viewType: Int): SectionType {
        return when (viewType) {
            in Int.MIN_VALUE..(-numberOfSections() - 15) -> SectionType.Header
            in (-numberOfSections() - 15)..0 -> SectionType.Footer
            else -> SectionType.Content
        }
    }

    enum class SectionType {
        Header,
        Content,
        Footer
    }
}