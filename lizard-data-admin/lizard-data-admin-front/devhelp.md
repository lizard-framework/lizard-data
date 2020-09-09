**1. 首页的Title、面包屑首页改为Lizard Data Admin的方式**
```javascript
修改 router/index.js

{
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Lizard Data Admin',
      component: () => import('@/views/dashboard/index'),
      meta: { title: 'Lizard Data Admin', icon: 'dashboard' }
    }]
}

修改 components/Breadcrumb/index.vue
methods: {
    getBreadcrumb() {
      // only show routes with meta.title
      let matched = this.$route.matched.filter(item => item.meta && item.meta.title)
      const first = matched[0]

      if (!this.isDashboard(first)) {
        matched = [{ path: '/dashboard', meta: { title: 'Lizard Data Admin' }}].concat(matched)
      }

      this.levelList = matched.filter(item => item.meta && item.meta.title && item.meta.breadcrumb !== false)
    },
    isDashboard(route) {
      const name = route && route.name
      if (!name) {
        return false
      }
      return name.trim().toLocaleLowerCase() === 'Lizard Data Admin'.toLocaleLowerCase()
},
```

**2. 添加API请求和Mock数据**
```javascript
// 1. 修改env.development, 请求不会自动加上前缀（production同理）
VUE_APP_BASE_API = ''

// 2. 在api文件夹和mock文件夹创建对应view路由的js文件，参考 database.js

// 3. 修改 mock/index.js文件，添加新的mock配置，否则404
const user = require('./user')
const table = require('./table')
const database = require('./database')

const mocks = [
  ...user,
  ...table,
  ...database
]
```