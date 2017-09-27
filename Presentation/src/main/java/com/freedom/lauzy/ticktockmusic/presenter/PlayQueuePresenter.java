package com.freedom.lauzy.ticktockmusic.presenter;

import com.freedom.lauzy.interactor.GetQueueUseCase;
import com.freedom.lauzy.ticktockmusic.base.BaseRxPresenter;
import com.freedom.lauzy.ticktockmusic.contract.PlayQueueContract;
import com.freedom.lauzy.ticktockmusic.function.RxHelper;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.model.mapper.SongMapper;

import javax.inject.Inject;

/**
 * Desc : 播放队列Presenter
 * Author : Lauzy
 * Date : 2017/9/8
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class PlayQueuePresenter extends BaseRxPresenter<PlayQueueContract.View>
        implements PlayQueueContract.Presenter {

    private GetQueueUseCase mGetQueueUseCase;

    @Inject
    PlayQueuePresenter(GetQueueUseCase getQueueUseCase) {
        mGetQueueUseCase = getQueueUseCase;
    }

    @Override
    public void loadQueueData(String[] ids) {
        if (ids != null) {
            mGetQueueUseCase.buildUseCaseObservable(ids)
                    .compose(RxHelper.ioMain())
                    .subscribe(queueSongBeen -> {
                        if (queueSongBeen != null) {
                            getView().loadQueueData(SongMapper.transform(queueSongBeen));
                        } else {
                            getView().emptyView();
                        }
                    });
        } else {
            getView().emptyView();
        }
    }

    @Override
    public void deleteQueueData(String[] ids, int position, SongEntity entity) {
        if (ids != null) {
            mGetQueueUseCase.deleteQueueObservable(ids)
                    .compose(RxHelper.ioMain())
                    .subscribe(integer -> getView().deleteItem(position));
        }
    }

    @Override
    public void deleteAllQueueData(String[] ids) {
        if (ids != null) {
            mGetQueueUseCase.deleteQueueObservable(ids)
                    .compose(RxHelper.ioMain())
                    .subscribe(integer -> getView().deleteAllQueueData());
        }
    }
}
