<template>
  <div class="app-container">
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="MixData名称">
        <template slot-scope="scope">
          {{ scope.row.mixDataName }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="MixData描述">
        <template slot-scope="scope">
          {{ scope.row.mixDataDesc }}
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { getDbMixDataList } from '@/api/database'

export default {
  data() {
    return {
      list: null,
      listLoading: true
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      getDbMixDataList().then(response => {
        this.list = response.data.items
        this.listLoading = false
      })
    }
  }
}
</script>
