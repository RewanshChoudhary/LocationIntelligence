import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'


export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {

      'process': 'process/browser',
      'buffer': 'buffer',
      'util': 'util'
    }
  },
  define: {
    global: 'globalThis',
    'process.env': {},
    'process.version': '"v16.0.0"',
    'process.platform': '"browser"'
  },
  optimizeDeps: {
    include: ['sockjs-client', 'buffer', 'process'],
    esbuildOptions: {
      define: {
        global: 'globalThis'
      }
    }
  },
  server: {
    fs: {
      allow: ['..']
    }
  },
  build: {
    commonjsOptions: {
      include: [/sockjs-client/, /node_modules/]
    }
  },
  ssr: {
    noExternal: ['sockjs-client']
  }
})
