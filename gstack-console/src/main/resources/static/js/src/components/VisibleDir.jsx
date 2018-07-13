import {connect} from 'react-redux'
import {addToCart, execDir} from "../actions";
import Dir from "./Dir";


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
        Object.assign(dirIdx, obj)
    })
    return dirIdx
}

const seekIndex = (idx, dir) => {
    let dirIdx = buildDirIndex(idx)
    let segs = dir.split(/[\\\/]/i)
    let dirObj = dirIdx
    segs.map(seg => {
        if (seg) {
            dirObj = dirObj[seg]
            if (typeof dirObj === "undefined")
                throw `dir not exists ${dir}`
            else if (dirObj == null)
                throw `dir is actually a file ${dir}`
        }
    })
    return dirObj;
}

const convertToItems = (dirObj) => {
    let items = []
    for (let p in dirObj) {
        let item = {name: p}
        let i = dirObj[p]
        item.itemtype = i == null ? 'document' : 'folder'
        items.push(item)
    }
    return items
}

// index <= state.index
// dir <= props.dir
const getVisibleItems = (index, dir) => {
    let idx = index.idx
    if (!idx) {
        idx = [{
            "file": ".\\gstack-workspace\\specs\\example.spec",
            "specs": [
                {
                    "title": "Specification Heading",
                    "lineNumber": 0,
                    "tags": [],
                    "scenarios": [
                        {
                            "title": "Scenario Heading",
                            "lineNumber": 7,
                            "tags": []
                        }
                    ]
                }
            ]
        }]
    }
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
        onExecuteClick: (suite, dir) => {
            dispatch(execDir(suite, dir))
        },
        onAddToCartClick: (suite, dir) => {
            dispatch(addToCart(suite, dir))
        },
    }
}

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(Dir)
