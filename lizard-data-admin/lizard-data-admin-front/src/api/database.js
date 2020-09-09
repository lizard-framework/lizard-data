import request from '@/utils/request'

// 获取数据库Mix-Data配置列表
export function getDbMixDataList(params) {
  return request({
    url: '/admin/database/list',
    method: 'get',
    params
  })
}

// 获取缓存Mix-Data配置列表
export function getCacheMixDataList(params) {
  return request({
    url: '/admin/cache-mix-data/list',
    method: 'get',
    params
  })
}
