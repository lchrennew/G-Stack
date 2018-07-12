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
    exec = require('child_process').exec


var webroot = "./static/";

var paths = {
    js: webroot + "js/src/**/*.js",
    minJs: webroot + "js/dist/**/*.min.js",
    css: webroot + "css/**/*.css",
    minCss: webroot + "css/**/*.min.css",
    concatJsDest: webroot + "js/site.min.js",
    concatCssDest: webroot + "css/site.min.css",
    less: webroot + "css/**/*.less",
    sass: webroot + "sass/**/*.scss",
    lessDest: webroot + "css",
    sassDest: webroot + "css",
    jsx: webroot + 'js/src/**/*.jsx',
    jsxDest: webroot + 'js/src',
    boardJs: webroot + 'js/src/Board/index.js',
    boardJsDest: webroot + 'js/dist',
};


gulp.task('less', function () {
    return gulp.src(paths.less)
        .pipe(less())
        .pipe(gulp.dest(paths.lessDest));
});


gulp.task('babel:jsx', function () {
    return gulp.src([paths.jsx, paths.js])
        .pipe(babel({
            presets: ['react', 'es2016', 'stage-3'],
            comments: false,
            minified: false,
            sourceMaps: true,
            retainLines: false,
        }))
        .pipe(gulp.dest(webroot + 'js/babel'))
})

gulp.task('rollup:js', ['babel:jsx'], function (cb) {
    var root = webroot + 'js/babel/',
        entry = root + 'index.js',
        output = webroot + 'js/dist/bundle.js',
        globals = [
            ['jquery', 'jQuery'],
            ['react', 'React'],
            ['react-dom', 'ReactDOM'],
            ['redux', 'Redux'],
            ['redux-thunk', 'ReduxThunk'],
            ['react-redux', 'ReactRedux'],
            ['react-router', 'ReactRouter'],
            ['react-router-dom', 'ReactRouterDOM'],
            ['whatwg-fetch', 'fetch'],
        ],
        shell = `rollup ${entry} -o ${output} -f umd -g ${globals.map(x=>x.join(':')).join(',')}`

    console.log(shell)
    exec(shell, (err, stdout, stderr)=> {
        console.log(stdout);
    console.log(stderr);
    cb(err);
})
})

gulp.task('bundle:js', ['rollup:js'], function () {
    return gulp.src(webroot + 'js/dist/usercenter/bundle.js')
        .pipe(babel({
            presets: ['babel-preset-es2015-script', 'stage-3'],
            comments: false,
            minified: true,
            compact: false,
            sourceMaps: true,
            retainLines: false,
        }))
        .pipe(gulp.dest(webroot + 'js/dist/min'))
})