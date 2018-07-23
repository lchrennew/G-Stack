import {connect} from 'react-redux'
import Dir from "./Dir";
import jQuery from 'jquery'


const buildDirIndex = (idx) => {
    let dirIdx = {}
    idx.map(f => {
        let obj = {},
            segs = f.file.split(/[\\\/]/i),
            pointer = obj,
            prev
        segs.map(seg => {
            if (seg === '') return
            if (seg === '.') return
            if (prev)
                pointer = pointer[prev] = {}
            pointer[seg] = null
            prev = seg
        })
        jQuery.extend(true, dirIdx, obj)
    })


    return dirIdx
}

const compress = (idxNode) => {
    let idx = jQuery.extend(true, {}, idxNode)
    // todo: compress idx
    let queue = Object.keys(idx).map(key => ({key, node: idx}))
    while (queue.length > 0) {
        let item = queue.shift(),
            child = item.node[item.key]
        if (child != null) {
            let keys = Object.keys(child)
            if (keys.length === 1) {
                let key = [item.key, keys[0]].join('/')
                queue.push({key, node: item.node})
                item.node[key] = child[keys[0]]
                delete item.node[item.key]
            }
            else if (keys.length > 1) {
                queue.push(...keys.map(key => ({key, node: child})))
            }
        }
    }
    return idx
}

const seekIndex = (idx, dir) => {
    let dirIdx = buildDirIndex(idx)
    let segs = dir.split(/[\\\/]/i)
    let idxNode = dirIdx
    segs.map(seg => {
        if (seg) {
            idxNode = idxNode[seg]
            if (typeof idxNode === "undefined")
                throw `dir not exists ${dir}`
            else if (idxNode == null)
                throw `dir is actually a file ${dir}`
        }
    })
    return compress(idxNode);
}

const convertToItems = (dirObj) => {
    let items = []
    for (let p in dirObj) {
        let item = {name: p}
        let i = dirObj[p]
        item.itemtype = i == null ? 'file' : 'folder'
        items.push(item)
    }
    return items
}

// index <= state.index
// dir <= props.dir
const getVisibleItems = (index, dir) => {
    let idx = index.idx || []
    // idx: see json/idx.json
    let dirObj = seekIndex(idx, dir)
    return convertToItems(dirObj)
}

const mapStateToProps = (state, props) => {
    return {
        items: getVisibleItems(state.index, props.dir)
    }
}

const mapDispatchToProps = dispatch => {
    return {
    }
}

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(Dir)
