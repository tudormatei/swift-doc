import React from 'react'

import './Collection.css';
import { DocumentCard } from '../../components';

const Collection = () => {
  return (
    <div className="app__collection">
      <DocumentCard />
      <DocumentCard />
      <DocumentCard />
      <DocumentCard />
    </div>
  )
}

export default Collection