var gulp = require("gulp"),
    // rimraf = require("rimraf"),
    concat = require("gulp-concat"),
    cssmin = require("gulp-cssmin"),
    uglify = require("gulp-uglify"),
    less = require('gulp-less'),
    sass = require('gulp-sass'),
    react = require('gulp-react'),
    babel = require('gulp-babel'),
    rollup = require('gulp-rollup'),
    copy = require('gulp-copy'),
    exec = require('child_process').exec


var webroot = "../resources/static/";
var srcroot = './'
//
// var paths = {
//     src: {
//         js: {
//             js: srcroot + "js/**/*.js",
//             jsx: srcroot + 'js/**/*.jsx',
//             min: webroot + "js/dist/**/*.min.js",
//             bundle: webroot + 'js/dist/bundle.js',
//         },
//         css: {
//             css: srcroot + "css/**/*.css",
//             less: srcroot + "css/**/*.less",
//             sass: srcroot + "sass/**/*.scss",
//         }
//     },
//     dist: {
//         js: {
//             babel: webroot + 'js/babel',
//             min: webroot + 'js/dist/min',
//             bundle: webroot + 'js/dist/bundle.js',
//             concat: webroot + "js/bundle.min.js"
//         },
//         css: {
//
//             min: webroot + "css/**/*.min.css",
//         }
//     },
//     minJs: webroot + "js/dist/**/*.min.js",
//     css: webroot + "css/**/*.css",
//     minCss: webroot + "css/**/*.min.css",
//     concatJsDest: webroot + "js/site.min.js",
//     concatCssDest: webroot + "css/site.min.css",
//
//     lessDest: webroot + "css",
//     sassDest: webroot + "css",
//     jsxDest: webroot + 'js/src',
// };

let paths = {
    js: {
        src: srcroot + 'js/**/*.js',
        dest: srcroot + 'target/babel/',
        jsx: {
            src: srcroot + 'js/**/*.jsx',
        },
        rollup: {
            src: srcroot + 'target/babel/index.js',
            dest: srcroot + 'target/umd/es6/app.js',
        },
        es6: {
            src: srcroot + 'target/umd/es6',
            dest: srcroot + 'target/umd/es5.min',
        },
        dist: {
            src: srcroot + 'target/umd/**/*.js',
            dest: webroot + 'js',
        }

    }
}

gulp.task('1 - Compile JSX into JS', function () {
    return gulp.src([paths.js.src, paths.js.jsx.src])
        .pipe(babel({
            presets: ['react', 'es2016', 'stage-3'],
            comments: false,
            minified: false,
            sourceMaps: true,
            retainLines: false,
        }))
        .pipe(gulp.dest(paths.js.dest))
})

gulp.task('2 - Package all compiled JS (Rollup)', ['1 - Compile JSX into JS'], function (cb) {
    var entry = paths.js.rollup.src,
        output = paths.js.rollup.dest,
        globals = [
            ['jquery', 'jQuery'],
            ['react', 'React'],
            ['react-dom', 'ReactDOM'],
            ['redux', 'Redux'],
            ['redux-thunk', 'ReduxThunk'],
            ['react-redux', 'ReactRedux'],
            ['react-router', 'ReactRouter'],
            ['react-router-dom', 'ReactRouterDOM'],
            ['cross-fetch', 'fetch'],
            ['stompjs', 'Stomp'],
            ['sockjs-client', 'SockJS'],
            ['react-notification-system', 'ReactNotificationSystem'],
            ['reactstrap','Reactstrap'],
            ['semantic-ui-react','semanticUIReact'],
        ],
        shell = `rollup ${entry} -o ${output} -f umd -g ${globals.map(x=>x.join(':')).join(',')}`

    console.log(shell)
    exec(shell, (err, stdout, stderr)=> {
        console.log(stdout);
    console.log(stderr);
    cb(err);
})
})

gulp.task('3 - Compile bundled js into previous version of Javascript', ['2 - Package all compiled JS (Rollup)'], function () {
    return gulp.src(paths.js.es6.src)
        .pipe(babel({
            presets: ['babel-preset-es2015', 'stage-3'],
            comments: false,
            minified: true,
            compact: false,
            sourceMaps: true,
            retainLines: false,
        }))
        // .pipe(gulp.dest(webroot + 'js/dist/min'))
        .pipe(gulp.dest(paths.js.es6.dest))
})

gulp.task('4 - Copy dist files to static folder', ['3 - Compile bundled js into previous version of Javascript'], function () {
    return gulp
        .src([paths.js.dist.src])
        .pipe(copy(paths.js.dist.dest, {prefix: 2}))
})