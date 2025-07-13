

import { defineConfig } from 'vite';


export default defineConfig({
    define: {
        global: {},
    },
    resolve: {
        alias: {
            // optional, in case "path" or "buffer" is required by a package
            path: 'path-browserify',
            stream: 'stream-browserify',
        },
    },
});
